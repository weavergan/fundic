package org.fund.stat.util;

import org.fund.stat.entity.Materiel;
import org.fund.stat.entity.Operation;
import org.fund.stat.entity.Record;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CaculateUtils {

    // 计算其它列
    public static void calculate2(List<Record> list, Materiel materiel) {
        // 设置第一行
        Record r = list.get(0);

        Float zs = 1F;// 累计折算
        Float fh = 0F;// 累计分红
        Float lastInvestValue = r.getValue();// 上次投入时的净值
        Float curMultiple = 0F; // 当前倍数
        if (r.getIncrease() < 0) {
            curMultiple = materiel.getBaseMultiple();
        }

        r.setSumInc(0.00F);
        r.setPurchasePrice(materiel.getTotalInvest().floatValue() / 100);
        r.setPurchaseCount(r.getPurchasePrice() / (1 + materiel.getPurchaseFee().floatValue() / 10000) / r.getValue());
        r.setSellCount(0F);
        r.setSellPrice(0F);
        r.setHoldCount(r.getPurchaseCount());
        r.setHoldPrice(r.getHoldCount() * r.getValue());
        r.setAllInvest(r.getPurchasePrice());
        r.setAllProfitPrice(r.getHoldPrice() * (1 - materiel.getSellFee().floatValue() / 10000) - r.getAllInvest());
        r.setAllProfitPot(r.getAllProfitPrice() / r.getAllInvest());
        r.setHoldValue(r.getAllInvest() / (r.getHoldCount() * (1 - materiel.getSellFee().floatValue() / 10000)));
        r.setAddRatio(0F);

        Float lastNot0PurchasePrice = r.getPurchasePrice();// 上一个不为0的申购金额
        Float maxAllInvest = r.getAllInvest();// 目前最大总投入

        Integer isIncrease = materiel.getIsIncrease();// 加仓优先：总投入超过首次投入的50倍的时候，加仓优先失效

        // 计算其它行
        for (int i = 1; i < list.size(); i++) {
            Record row = list.get(i);
            Record lastRow = list.get(i - 1);

            //到达风控仓位时，自动取消加仓优先
            if(isIncrease == 1 && lastRow.getAllInvest() * 100 / materiel.getTotalInvest() > materiel.getRiskRate()) {
                isIncrease = 0;
            }

            Float curZS = 1F;
            Float curFH = 0F;
            if (row.getFenhong() != null && row.getFenhong().length() > 0 && row.getFenhong().indexOf("折算") > 0) {
                String temp = row.getFenhong().substring(0, row.getFenhong().lastIndexOf("份"))
                        .substring(row.getFenhong().indexOf("每份基金份额折算") + "每份基金份额折算".length());
                curZS = Float.parseFloat(temp);
                zs = zs * curZS;
            } else if (row.getFenhong() != null && row.getFenhong().length() > 0 && row.getFenhong().indexOf("派现金") > 0) {
                String temp = row.getFenhong().substring(0, row.getFenhong().lastIndexOf("元"))
                        .substring(row.getFenhong().indexOf("每份派现金") + "每份派现金".length());
                curFH = Float.parseFloat(temp);
                fh = fh + curFH;
            }
            row.setIncrease((float) ((row.getValue() + curFH) * curZS / lastRow.getValue() - 1));
            // 当前收益率
            float curProfitPot = lastRow.getHoldCount() * (row.getValue() + curFH) * curZS
                    * (1 - materiel.getSellFee().floatValue() / 10000) / lastRow.getAllInvest() - 1;

            row.setSumInc((float) ((row.getValue() + fh) * zs / r.getValue() - 1));

            if (row.getFundStatus().equals("暂停申购") || row.getFundStatus().equals("封闭期")) {
                curMultiple = 0F;
            } else {
                if (isIncrease == 1) { // 加仓优先被选中
                    if (row.getIncrease() > materiel.getStartInvest().floatValue() / 10000) {
                        curMultiple = 0F;
                    } else {
                        if(curProfitPot > materiel.getBaseMultiple() * (materiel.getStartInvest().floatValue() / 10000
                                - materiel.getPurchaseFee().floatValue() / 10000 - materiel.getSellFee().floatValue() / 10000)) {
//                        if (lastRow.getHoldCount() * row.getValue() * (1 - materiel.getSellFee().floatValue() / 10000)
//                                / lastRow.getAllInvest() - 1 + materiel.getPurchaseFee().floatValue() / 10000
//                                + materiel.getSellFee().floatValue() / 10000 > materiel.getBaseMultiple()
//                                * materiel.getStartInvest().floatValue() / 10000) {
                            curMultiple = 0F;
                        } else {
                            if (curMultiple == 0) {
                                curMultiple = materiel.getBaseMultiple();
                            } else {
                                curMultiple += materiel.getMoreMultiple();
                            }
                            lastInvestValue = (float) ((row.getValue() + fh) * zs);
                        }
                    }
                } else {// 加仓优先未选中
                    if ((row.getValue() + fh) * zs / lastInvestValue - 1 > materiel.getStartInvest().floatValue() / 10000) {
                        curMultiple = 0F;
                    } else {
                        if (curMultiple == 0) {
                            curMultiple = materiel.getBaseMultiple();
                        } else {
                            curMultiple += materiel.getMoreMultiple();
                        }
                        lastInvestValue = (float) ((row.getValue() + fh) * zs);
                    }
                }
            }
            row.setPurchasePrice(lastNot0PurchasePrice * curMultiple);
            if (materiel.getIsSetMinRate() == 1) { // 设置保证追加比
                if (row.getPurchasePrice() != 0 && row.getPurchasePrice() / lastRow.getAllInvest() < 0.33) {
                    row.setPurchasePrice(lastRow.getAllInvest() / 3);
                }
            }
            //资金不够用，自动修正
            if(row.getPurchasePrice() + lastRow.getAllInvest() > materiel.getTotalInvest()) {
                row.setPurchasePrice(materiel.getTotalInvest() - lastRow.getAllInvest());
            }

            if (row.getPurchasePrice() != 0) {
                lastNot0PurchasePrice = row.getPurchasePrice();
            }

            row.setPurchaseCount(row.getPurchasePrice() / (1 + materiel.getPurchaseFee().floatValue() / 10000)
                    / row.getValue());

            Float temp = 0F;
            if (materiel.getIsAutoSell() != null
                    && materiel.getIsAutoSell() == 1
                    && !row.getFundSellStatus().equals("暂停赎回")
                    && !row.getFundSellStatus().equals("封闭期")
                    && (lastRow.getHoldCount() * row.getValue() * (1 - materiel.getSellFee().floatValue() / 10000)
                            / lastRow.getAllInvest() - 1 >= materiel.getStartSell().floatValue() / 10000)) {
                temp = lastRow.getHoldCount() - maxAllInvest / (1 - materiel.getSellFee().floatValue() / 10000)
                        / row.getValue();
                if (temp < materiel.getMinSellCount() || lastRow.getHoldCount() - temp < materiel.getMinHoldCount()) {
                    temp = 0F;
                }
            }
            row.setSellCount(temp);

            row.setSellPrice(row.getSellCount() * row.getValue() * (1 - materiel.getSellFee().floatValue() / 10000));

            if (row.getFenhong() != null && row.getFenhong().length() > 0) {
                if (row.getFenhong().indexOf("每份基金份额折算") >= 0) {
                    row.setHoldCount((float) (lastRow.getHoldCount() * curZS));
                } else if (row.getFenhong().indexOf("每份派现金") >= 0 && materiel.getIsFenhongInvest() == 1) {
                    row.setHoldCount((float) (lastRow.getHoldCount() + row.getPurchaseCount() - row.getSellCount() + lastRow
                            .getHoldCount()
                            * curFH
                            / (1 + materiel.getPurchaseFee().floatValue() / 10000)
                            / row.getValue()));
                } else {
                    row.setHoldCount(lastRow.getHoldCount() + row.getPurchaseCount() - row.getSellCount());
                }
            } else {
                row.setHoldCount(lastRow.getHoldCount() + row.getPurchaseCount() - row.getSellCount());
            }

            row.setHoldPrice(row.getHoldCount() * row.getValue());

            // 总投入
            if (row.getFenhong() != null && row.getFenhong().length() > 0 && row.getFenhong().indexOf("每份派现金") > 0
                    && materiel.getIsFenhongInvest() != 1) {
                row.setAllInvest((float) (lastRow.getAllInvest() + row.getPurchasePrice() - row.getSellPrice() - lastRow
                        .getHoldCount() * curFH));
            } else {
                row.setAllInvest(lastRow.getAllInvest() + row.getPurchasePrice() - row.getSellPrice());
            }
            if (row.getAllInvest() > maxAllInvest) {
                maxAllInvest = row.getAllInvest();
            }
            // 净盈亏
            row.setAllProfitPrice(row.getHoldPrice() * (1 - materiel.getSellFee().floatValue() / 10000)
                    - row.getAllInvest());
            // 收益率
            row.setAllProfitPot(row.getAllProfitPrice() / row.getAllInvest());

            // 持仓成本
            row.setHoldValue(row.getAllInvest() / row.getHoldCount() / (1 - materiel.getSellFee().floatValue() / 10000));
            // 追加比
            // row.setAddRatio(row.getPurchasePrice() / lastRow.getAllInvest());

            // 总投入超过首次投入的50倍的时候，加仓优先失效
            if (r.getAllInvest() * 50 < row.getAllInvest()) {
                isIncrease = 0;
            }
        }
    }


    public static void calculateForActual(List<Record> list, Materiel materiel, List<Operation> operations) {
        Map<String, Operation> operationMap = new HashMap<String, Operation>();
        for(Operation operation : operations) {
            operationMap.put(operation.getDate(), operation);
        }


        // 设置第一行
        Record r = list.get(0);

        Float zs = 1F;// 累计折算
        Float fh = 0F;// 累计分红
        Float lastInvestValue = r.getValue();// 上次投入时的净值
        Float curMultiple = 0F; // 当前倍数
        if (r.getIncrease() < 0) {
            curMultiple = materiel.getBaseMultiple();
        }

        r.setSumInc(0.00F);
        if(operationMap.containsKey(r.getDate())) {
            r.setPurchasePrice(operationMap.get(r.getDate()).getPurchasePrice().floatValue() / 100);
            r.setSellCount(operationMap.get(r.getDate()).getSellCount());
        } else {
            r.setPurchasePrice(0F);
            r.setSellCount(0F);
        }
        r.setPurchaseCount(r.getPurchasePrice() / (1 + materiel.getPurchaseFee().floatValue() / 10000) / r.getValue());
        r.setSellPrice(r.getSellCount() * r.getValue() * (1 - materiel.getSellFee().floatValue() / 10000));

        r.setHoldCount(r.getPurchaseCount() - r.getSellCount());
        r.setHoldPrice(r.getHoldCount() * r.getValue());
        r.setAllInvest(r.getPurchasePrice() - r.getSellPrice());
        r.setAllProfitPrice(r.getHoldPrice() * (1 - materiel.getSellFee().floatValue() / 10000) - r.getAllInvest());
        if(r.getAllInvest() == 0) {
            r.setAllProfitPot(0F);
            r.setHoldValue(0F);
        } else {
            r.setAllProfitPot(r.getAllProfitPrice() / r.getAllInvest());
            r.setHoldValue(r.getAllInvest() / (r.getHoldCount() * (1 - materiel.getSellFee().floatValue() / 10000)));
        }
        r.setAddRatio(0F);

        Float lastNot0PurchasePrice = r.getPurchasePrice();// 上一个不为0的申购金额
        Float maxAllInvest = r.getAllInvest();// 目前最大总投入

        Integer isIncrease = materiel.getIsIncrease();// 加仓优先：总投入超过首次投入的50倍的时候，加仓优先失效

        // 计算其它行
        for (int i = 1; i < list.size(); i++) {
            Record row = list.get(i);
            Record lastRow = list.get(i - 1);

            //到达风控仓位时，自动取消加仓优先
            if(isIncrease == 1 && lastRow.getAllInvest() * 100 / materiel.getTotalInvest() > materiel.getRiskRate()) {
                isIncrease = 0;
            }

            Float curZS = 1F;
            Float curFH = 0F;
            if (row.getFenhong() != null && row.getFenhong().length() > 0 && row.getFenhong().indexOf("折算") > 0) {
                String temp = row.getFenhong().substring(0, row.getFenhong().lastIndexOf("份"))
                        .substring(row.getFenhong().indexOf("每份基金份额折算") + "每份基金份额折算".length());
                curZS = Float.parseFloat(temp);
                zs = zs * curZS;
            } else if (row.getFenhong() != null && row.getFenhong().length() > 0 && row.getFenhong().indexOf("派现金") > 0) {
                String temp = row.getFenhong().substring(0, row.getFenhong().lastIndexOf("元"))
                        .substring(row.getFenhong().indexOf("每份派现金") + "每份派现金".length());
                curFH = Float.parseFloat(temp);
                fh = fh + curFH;
            }
            row.setIncrease((float) ((row.getValue() + curFH) * curZS / lastRow.getValue() - 1));
            // 当前收益率
            float curProfitPot = lastRow.getHoldCount() * (row.getValue() + curFH) * curZS
                    * (1 - materiel.getSellFee().floatValue() / 10000) / lastRow.getAllInvest() - 1;

            row.setSumInc((float) ((row.getValue() + fh) * zs / r.getValue() - 1));

            if(operationMap.containsKey(row.getDate())) {
                row.setPurchasePrice(operationMap.get(row.getDate()).getPurchasePrice().floatValue() / 100);
                row.setSellCount(operationMap.get(row.getDate()).getSellCount());
            } else {
                row.setPurchasePrice(0F);
                row.setSellCount(0F);
            }
            row.setPurchaseCount(row.getPurchasePrice() / (1 + materiel.getPurchaseFee().floatValue() / 10000)
                    / row.getValue());

            row.setSellPrice(row.getSellCount() * row.getValue() * (1 - materiel.getSellFee().floatValue() / 10000));

            if (row.getFenhong() != null && row.getFenhong().length() > 0) {
                if (row.getFenhong().indexOf("每份基金份额折算") >= 0) {
                    row.setHoldCount((float) (lastRow.getHoldCount() * curZS));
                } else if (row.getFenhong().indexOf("每份派现金") >= 0 && materiel.getIsFenhongInvest() == 1) {
                    row.setHoldCount((float) (lastRow.getHoldCount() + row.getPurchaseCount() - row.getSellCount() + lastRow
                            .getHoldCount()
                            * curFH
                            / (1 + materiel.getPurchaseFee().floatValue() / 10000)
                            / row.getValue()));
                } else {
                    row.setHoldCount(lastRow.getHoldCount() + row.getPurchaseCount() - row.getSellCount());
                }
            } else {
                row.setHoldCount(lastRow.getHoldCount() + row.getPurchaseCount() - row.getSellCount());
            }

            row.setHoldPrice(row.getHoldCount() * row.getValue());

            // 总投入
            if (row.getFenhong() != null && row.getFenhong().length() > 0 && row.getFenhong().indexOf("每份派现金") > 0
                    && materiel.getIsFenhongInvest() != 1) {
                row.setAllInvest((float) (lastRow.getAllInvest() + row.getPurchasePrice() - row.getSellPrice() - lastRow
                        .getHoldCount() * curFH));
            } else {
                row.setAllInvest(lastRow.getAllInvest() + row.getPurchasePrice() - row.getSellPrice());
            }
            if (row.getAllInvest() > maxAllInvest) {
                maxAllInvest = row.getAllInvest();
            }
            // 净盈亏
            row.setAllProfitPrice(row.getHoldPrice() * (1 - materiel.getSellFee().floatValue() / 10000)
                    - row.getAllInvest());

            // 收益率 持仓成本
            if(row.getAllInvest() == 0) {
                row.setAllProfitPot(0F);
                row.setHoldValue(0F);
            } else {
                row.setAllProfitPot(row.getAllProfitPrice() / row.getAllInvest());
                row.setHoldValue(row.getAllInvest() / row.getHoldCount() / (1 - materiel.getSellFee().floatValue() / 10000));
            }

            // 追加比
            // row.setAddRatio(row.getPurchasePrice() / lastRow.getAllInvest());

            // 总投入超过首次投入的50倍的时候，加仓优先失效
            if (r.getAllInvest() * 50 < row.getAllInvest()) {
                isIncrease = 0;
            }
        }
    }

    public static void round(List<Record> recordList) {
        for (Record r : recordList) {
            BigDecimal b = new BigDecimal(r.getValue());
            r.setValue(b.setScale(4, BigDecimal.ROUND_HALF_UP).floatValue());

            b = new BigDecimal(r.getIncrease() * 100);// 百分比
            r.setIncrease(b.setScale(2, BigDecimal.ROUND_HALF_UP).floatValue());

            b = new BigDecimal(r.getSumInc() * 100);// 百分比
            r.setSumInc(b.setScale(2, BigDecimal.ROUND_HALF_UP).floatValue());

            b = new BigDecimal(r.getPurchasePrice());
            r.setPurchasePrice(b.setScale(2, BigDecimal.ROUND_HALF_UP).floatValue());

            b = new BigDecimal(r.getPurchaseCount());
            r.setPurchaseCount(b.setScale(2, BigDecimal.ROUND_HALF_UP).floatValue());

            b = new BigDecimal(r.getSellCount());
            r.setSellCount(b.setScale(2, BigDecimal.ROUND_HALF_UP).floatValue());

            b = new BigDecimal(r.getSellPrice());
            r.setSellPrice(b.setScale(2, BigDecimal.ROUND_HALF_UP).floatValue());

            b = new BigDecimal(r.getHoldCount());
            r.setHoldCount(b.setScale(2, BigDecimal.ROUND_HALF_UP).floatValue());

            b = new BigDecimal(r.getHoldPrice());
            r.setHoldPrice(b.setScale(2, BigDecimal.ROUND_HALF_UP).floatValue());

            b = new BigDecimal(r.getAllInvest());
            r.setAllInvest(b.setScale(2, BigDecimal.ROUND_HALF_UP).floatValue());

            b = new BigDecimal(r.getAllProfitPrice());
            r.setAllProfitPrice(b.setScale(2, BigDecimal.ROUND_HALF_UP).floatValue());

            b = new BigDecimal(r.getAllProfitPot() * 100);
            r.setAllProfitPot(b.setScale(2, BigDecimal.ROUND_HALF_UP).floatValue());

            b = new BigDecimal(r.getHoldValue());
            r.setHoldValue(b.setScale(4, BigDecimal.ROUND_HALF_UP).floatValue());

            // b = new BigDecimal(r.getAddRatio());
            // r.setAddRatio(b.setScale(2,
            // BigDecimal.ROUND_HALF_UP).floatValue());
        }
    }
}
