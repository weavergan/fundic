package org.fund.stat.service.impl;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.fund.common.ConstantEnum.AuthType;
import org.fund.exception.NoDataException;
import org.fund.stat.FundConstant;
import org.fund.stat.dao.FundDao;
import org.fund.stat.entity.Materiel;
import org.fund.stat.entity.Operation;
import org.fund.stat.entity.Record;
import org.fund.stat.entity.SMSScription;
import org.fund.stat.service.FundService;
import org.fund.stat.util.CaculateUtils;
import org.fund.stat.util.GetDataUtils;
import org.fund.util.DateUtil;
import org.htmlparser.NodeFilter;
import org.htmlparser.Parser;
import org.htmlparser.filters.TagNameFilter;
import org.htmlparser.tags.TableColumn;
import org.htmlparser.tags.TableRow;
import org.htmlparser.tags.TableTag;
import org.htmlparser.util.NodeList;
import org.htmlparser.util.ParserException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.math.BigDecimal;
import java.util.*;

@Service("fundService")
public class FundServiceImpl implements FundService {

    @Autowired
    private FundDao fundDao;

    private static Log log = LogFactory.getLog(FundServiceImpl.class);

    @Override
    public List<Materiel> getMaterielsByUserId(Long userId) {
        List<Materiel> list = fundDao.getMaterielsById(userId);
        if (CollectionUtils.isEmpty(list)) {
            return list;
        }

        Collections.sort(list, new Comparator<Materiel>() {

            @Override
            public int compare(Materiel m1, Materiel m2) {
                if (m1.getUpdateTime() == null)
                    return 1;
                else if (m2.getUpdateTime() == null)
                    return -1;
                if (m1.getUpdateTime().before(m2.getUpdateTime())) {
                    return 1;
                } else if (m1.getUpdateTime().after(m2.getUpdateTime())) {
                    return -1;
                } else {
                    return 0;
                }
            }
        });
        list.get(0).setSelected(1);
        for (int i = 1; i < list.size(); i++) {
            list.get(i).setSelected(0);
        }
        return list;
    }

    @Override
    public List<Record> getListByCode(Materiel materiel, Integer guzhiFrom, Integer auth, boolean isActual, Long userId) throws NoDataException {
        String url = FundConstant.stat_list_url.replace("#{code}", materiel.getCode())
                .replace("#{startDate}", materiel.getStartDate()).replace("#{endDate}", materiel.getEndDate())
                .replace("#{rand}", String.valueOf(Math.random()));

        Parser parser = GetDataUtils.getParser(url, FundConstant.stat_list_url_encoding);
        // 解析数据
        List<Record> recordList = parse(parser);
        // 反序
        Collections.reverse(recordList);
        // 如果结束日期是今天，需要添加一条
        addTodayList(recordList, materiel, guzhiFrom, auth);
        // 如果没有数据抛出异常
        if (CollectionUtils.isEmpty(recordList)) {
            throw new NoDataException();
        }
        // 计算其它列
        if(!isActual) {
            CaculateUtils.calculate2(recordList, materiel);
        } else {
            //实际操作
            List<Operation> operations = fundDao.getOperationsByUserIdAndCode(userId.intValue(), materiel.getCode());
            CaculateUtils.calculateForActual(recordList, materiel, operations);
        }
        // 保留两位小数
        CaculateUtils.round(recordList);

        return recordList;
    }

    // 如果结束日期是今天，需要添加一条
    private void addTodayList(List<Record> recordList, Materiel materiel, Integer guzhiFrom, Integer auth) {
        if (auth == AuthType.NORMAL_USER.getId()) { // 普通用户无法获取当天数据
            return;
        }
        Date today = new Date();
        if (DateUtil.dateToString(today).equals(materiel.getEndDate())) {
            if (recordList.size() > 0 && recordList.get(recordList.size() - 1).getDate().equals(materiel.getEndDate())) {// 当日净值已经公布，不需要添加
                return;
            }
            try {
                Float[] valuation = getValuation(materiel.getCode(), guzhiFrom);
                Record todayRecord = new Record();
                todayRecord.setDate(materiel.getEndDate());
                todayRecord.setValue(valuation[0]);
                todayRecord.setFundStatus("");// 暂不读取申购状态和分红
                todayRecord.setFundSellStatus("");
                todayRecord.setFenhong("");
                todayRecord.setIncrease(0F);
                recordList.add(todayRecord);
            } catch (Exception e) {
                log.error("获取估值失败", e);
                return;
            }

        }
    }

    private static List<Record> parse(Parser parser) throws NoDataException {
        if (parser == null) {
            return Collections.emptyList();
        }

        List<Record> result = new ArrayList<Record>();

        NodeFilter filter = new TagNameFilter("table");
        NodeList content = null;
        try {
            content = parser.extractAllNodesThatMatch(filter);
        } catch (ParserException e) {}
        if (content != null) {
            TableTag tag = (TableTag) content.elementAt(0);
            if (tag.toPlainTextString().contains("暂无数据")) {
                return result;
            }
            TableRow[] rows = tag.getRows();
            for (TableRow row : rows) {
                TableColumn[] cols = row.getColumns();
                if (cols.length < 7) {// thead
                    continue;
                }
                Record r = new Record();
                r.setDate(cols[0].toPlainTextString());
                r.setValue(Float.parseFloat(cols[1].toPlainTextString()));
                r.setIncrease(Float.parseFloat(cols[3].toPlainTextString().substring(0,
                        cols[3].toPlainTextString().length() - 1)) / 100);
                r.setFundStatus(cols[4].toPlainTextString());
                r.setFundSellStatus(cols[5].toPlainTextString());
                r.setFenhong(cols[6].toPlainTextString());

                result.add(r);
            }
        }

        return result;
    }

    @Override
    public void saveData(Materiel materiel) {
        Materiel m = fundDao.getMaterielByCode(materiel.getUserId(), materiel.getCode());
        if (m != null) {
            fundDao.updateMaterielsByCode(materiel);
        } else {
            fundDao.addMaterielsByCode(materiel);
        }
    }

    @Override
    public Float[] getValuation(String code, Integer guzhiFrom) throws Exception {
        if (guzhiFrom == 1) { // 好买基金
            String url = FundConstant.stat_valuation_haomai.replace("#{code}", code).replace("#{rand}",
                    String.valueOf(Math.random()));

            String json;
            try {
                json = GetDataUtils.getSourceCode(url, FundConstant.stat_list_url_encoding);
            } catch (Exception e) {
                log.error("获取实时估值失败！", e);
                throw new Exception();
            }
            // 解析数据
            Float[] result = parseValuationForHaomai(json);

            return result;
        } else if (guzhiFrom == 2) { // 爱基金
            String url = FundConstant.stat_valuation_aijijin.replace("#{code}", code).replace("#{rand}",
                    String.valueOf(Math.random()));

            String json;
            try {
                json = GetDataUtils.getSourceCode(url, FundConstant.stat_list_url_encoding);
            } catch (Exception e) {
                log.error("获取实时估值失败！", e);
                throw new Exception();
            }
            // 解析数据
            Float[] result = parseValuationForAijijin(json);

            return result;
        } else { // 天天基金
            String url = FundConstant.stat_valuation_tiantian.replace("#{code}", code).replace("#{rand}",
                    String.valueOf(Math.random()));

            String json;
            try {
                json = GetDataUtils.getSourceCode(url, FundConstant.stat_list_url_encoding);
            } catch (Exception e) {
                log.error("获取实时估值失败！", e);
                throw new Exception();
            }
            // 解析数据
            Float[] result = parseValuationForTiantian(json);

            return result;
        }
    }

    private Float[] parseValuationForTiantian(String data) {
        Float[] result = new Float[2];
        String parts[] = data.split(",");
        for (String part : parts) {
            if (part.contains("gsz")) {
                String part1[] = part.split(":");
                if (part1[0].equals("\"gsz\"")) {
                    Float f = Float.parseFloat(part1[1].substring(1, part1[1].length() - 1));
                    result[0] = f;
                    continue;
                } else if (part1[0].equals("\"gszzl\"")) {
                    Float f = Float.parseFloat(part1[1].substring(1, part1[1].length() - 1));
                    result[1] = f;
                    break;
                }
            }
        }
        return result;
    }

    private Float[] parseValuationForHaomai(String data) {
        Float[] result = new Float[2];
        String parts[] = data.split("</span>");
        for (String part : parts) {
            if (part.contains("con_value")) {
                String part1[] = part.split(">");
                if (part1[1].contains("con_value")) {
                    Float f = Float.parseFloat(part1[2]);
                    result[0] = f;
                    continue;
                }
            } else if (part.contains("%")) {
                String part1[] = part.split(">");
                if (part1[1].contains("%")) {
                    Float f = Float.parseFloat(part1[1].substring(0, part1[1].length() - 1));
                    result[1] = f;
                    break;
                }
            }
        }
        return result;
    }

    private Float[] parseValuationForAijijin(String data) {
        Float[] result = new Float[2];
        Float value = 0F;
        Float pre = 0F;

        String parts[] = data.split(",");
        for (String part : parts) {
            if (part.contains("value")) {
                String part1[] = part.split(":");
                if (part1[0].equals("\"value\"")) {
                    value = Float.parseFloat(part1[1].substring(1, part1[1].length() - 1));
                    result[0] = value;
                    continue;
                }
            } else if (part.contains("pre")) {
                String part1[] = part.split(":");
                if (part1[0].equals("\"pre\"")) {
                    pre = Float.parseFloat(part1[1].substring(1, part1[1].length() - 1));
                    result[1] = BigDecimal.valueOf((value - pre) / pre * 100).setScale(2, BigDecimal.ROUND_HALF_UP)
                            .floatValue();
                    continue;
                }
            }
        }
        return result;
    }

    @Override
    public Materiel getMaterielByCode(Long userId, String code) {
        return fundDao.getMaterielByCode(userId, code);
    }

    @Override
    public void deleteFundData(Long userId, String code) {
        fundDao.deleteFundData(userId, code);
        fundDao.cancelSmsSub(userId, code);
    }

    @Override
    public void smsSubscription(Long userId, String code) {
        fundDao.smsSubscription(userId, code);
    }

    @Override
    public void cancelSmsSub(Long userId, String code) {
        fundDao.cancelSmsSub(userId, code);
    }

    @Override
    public SMSScription getSmsSubscription(Long userId, String code) {
        return fundDao.getSmsSubscription(userId, code);
    }

    @Override
    public void setOperation(Operation operation) {
        Operation m = fundDao.getOperationsByUserIdAndCodeAndDate(operation.getUserId(), operation.getCode(), operation.getDate());
        if (m != null) {
            fundDao.updateOperation(operation);
        } else {
            fundDao.addOperation(operation);
        }
    }
}
