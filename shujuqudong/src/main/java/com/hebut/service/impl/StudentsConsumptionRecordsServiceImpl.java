package com.hebut.service.impl;

import cn.hutool.core.date.DateTime;
import cn.hutool.core.date.DateUtil;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.hebut.api.PythonAPIClient;
import com.hebut.dto.StudentsConsumeDTO;
import com.hebut.dto.StudentsInfoDTO;
import com.hebut.entity.StudentsConsumptionRecords;
import com.hebut.mapper.StudentsConsumptionRecordsMapper;
import com.hebut.service.StudentsConsumptionRecordsService;
import com.hebut.vo.StudentsConsumptionRecordsVO;
import lombok.SneakyThrows;
import org.apache.ibatis.session.SqlSession;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.awt.print.Printable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

@Service
public class StudentsConsumptionRecordsServiceImpl implements StudentsConsumptionRecordsService {

    @Autowired
    private StudentsConsumptionRecordsMapper studentsConsumptionRecordsMapper;
    private SqlSession sqlSession;

    @SneakyThrows
    @Override
    public PageInfo<StudentsInfoDTO> abnormal(StudentsConsumptionRecordsVO studentsConsumptionRecordsVO, Integer pageNum, Integer pageSize) {
        List<StudentsConsumptionRecords> studentsConsumptionRecordsList = studentsConsumptionRecordsMapper.abnormal(studentsConsumptionRecordsVO);


        // 转成JSON格式
        JSONArray recordsArray = new JSONArray();
        for (StudentsConsumptionRecords record : studentsConsumptionRecordsList) {
            JSONObject recordJson = new JSONObject();
            recordJson.put("XH", record.getXH());
            recordJson.put("XFDD", record.getXFDD());
            recordJson.put("XFJE", record.getXFJE());
            recordJson.put("XFSJ", record.getXFSJ());
            recordJson.put("ID", record.getID());
            recordsArray.put(recordJson);
        }
        JSONArray api = PythonAPIClient.API(recordsArray, "all_predict");   // api 是python的接口

        // Convert the first JSONArray (strings) to String[]
        JSONArray stringArray = api.getJSONArray(0);
        String[] xhList1 = new String[stringArray.length()];
        for (int i = 0; i < stringArray.length(); i++) {
            xhList1[i] = stringArray.getString(i);
        }

        List<String> xhList = new ArrayList<>();
        for (int i = 0; i < xhList1.length; i++) {
            xhList.add(xhList1[i]);
        }


        List<StudentsInfoDTO> studentsInfoList = studentsConsumptionRecordsMapper.selectBy(xhList);


        // pageNum代表页码值，pageSize代表每页条数
        PageHelper.startPage(pageNum, pageSize);

        // 使用PageInfo来封装查询的数据
        PageInfo<StudentsInfoDTO> pageInfo = new PageInfo<StudentsInfoDTO>(studentsInfoList);
        return pageInfo;
    }

    @SneakyThrows
    @Override
    public StudentsConsumeDTO selectByCondition(StudentsConsumptionRecordsVO studentsConsumptionRecordsVO) {
        Date beginDate = DateUtil.parse(studentsConsumptionRecordsVO.getBEGIN(), "yyyy-MM-dd");
        Date endDate = DateUtil.parse(studentsConsumptionRecordsVO.getEND(), "yyyy-MM-dd");


        // 计算开始日期和结束日期之间的天数差
        long daysBetween = DateUtil.betweenDay(beginDate, endDate, true);

        // 创建一个数组来存储这些日期
        String[] dateRange = new String[(int) daysBetween];

        // 填充数组
        for (int i = 0; i < daysBetween; i++) {
            DateTime date = DateUtil.offsetDay(beginDate, i);
            dateRange[i] = DateUtil.format(date, "yyyy-MM-dd");
        }

        List<StudentsConsumptionRecords> studentsConsumptionRecordsList = studentsConsumptionRecordsMapper.selectByCondition(studentsConsumptionRecordsVO);
        if (studentsConsumptionRecordsList.isEmpty()) {
            StudentsConsumeDTO studentsConsumeDTO = new StudentsConsumeDTO();
            studentsConsumeDTO.setTime(dateRange);
            return studentsConsumeDTO;
        } else {
            // 转成JSON格式
            JSONArray recordsArray = new JSONArray();
            for (StudentsConsumptionRecords record : studentsConsumptionRecordsList) {
                JSONObject recordJson = new JSONObject();
                recordJson.put("XH", record.getXH());
                recordJson.put("XFDD", record.getXFDD());
                recordJson.put("XFJE", record.getXFJE());
                recordJson.put("XFSJ", record.getXFSJ());
                recordJson.put("ID", record.getID());
                recordsArray.put(recordJson);
            }
            JSONArray api = PythonAPIClient.API(recordsArray, "predict");   // api 是python的接口
            System.out.println(api);
            // Convert the first JSONArray (strings) to String[]
            JSONArray stringArray = api.getJSONArray(0);
            String[] dates = new String[stringArray.length()];
            for (int i = 0; i < stringArray.length(); i++) {
                dates[i] = stringArray.getString(i);
            }

            JSONArray stringArray1 = api.getJSONArray(1);
            String[] dates1 = new String[stringArray1.length()];
            for (int i = 0; i < stringArray1.length(); i++) {
                dates1[i] = stringArray1.getString(i);
            }
            JSONArray stringArray2 = api.getJSONArray(2);
            String[] dates2 = new String[stringArray2.length()];
            for (int i = 0; i < stringArray2.length(); i++) {
                dates2[i] = stringArray2.getString(i);
            }
            JSONArray stringArray3 = api.getJSONArray(3);
            String[] dates3 = new String[stringArray3.length()];
            for (int i = 0; i < stringArray3.length(); i++) {
                dates3[i] = stringArray3.getString(i);
            }
            JSONArray stringArray4 = api.getJSONArray(4);
            String[] dates4 = new String[stringArray4.length()];
            for (int i = 0; i < stringArray4.length(); i++) {
                dates4[i] = stringArray4.getString(i);
            }
            // 解决天数问题
            int i = 0, j = 0;
            int n = dateRange.length;
            String[] zao = new String[dateRange.length];
            String[] zhong = new String[dateRange.length];
            String[] wan = new String[dateRange.length];
            while (i < n) {
                if (dateRange[i].equals(dates[j])) {
                    zao[i] = dates1[j];
                    zhong[i] = (dates2[j]);
                    wan[i] = (dates3[j]);
                    if (j < dates.length - 1) {
                        j++;
                    }
                } else {
                    zao[i] = "0";
                    zhong[i] = "0";
                    wan[i] = "0";
                }
                i++;
            }


            StudentsConsumeDTO studentsConsumeDTO = new StudentsConsumeDTO();
            studentsConsumeDTO.setTime(dateRange);
            studentsConsumeDTO.setZao(zao);
            studentsConsumeDTO.setZhong(zhong);
            studentsConsumeDTO.setWan(wan);
            studentsConsumeDTO.setPoor(dates4);
            studentsConsumeDTO.calculateSum();

            return studentsConsumeDTO;
        }
    }

    @SneakyThrows
    @Override
    public StudentsConsumeDTO selectByConditionAll(StudentsConsumptionRecordsVO studentsConsumptionRecordsVO) {
        List<StudentsConsumptionRecords> studentsConsumptionRecordsList = studentsConsumptionRecordsMapper.selectByConditionAll(studentsConsumptionRecordsVO);
        List<StudentsConsumptionRecords> studentsConsumptionRecordsList1 = studentsConsumptionRecordsMapper.selectByCondition(studentsConsumptionRecordsVO);
        Date beginDate = DateUtil.parse(studentsConsumptionRecordsVO.getBEGIN(), "yyyy-MM-dd");
        Date endDate = DateUtil.parse(studentsConsumptionRecordsVO.getEND(), "yyyy-MM-dd");

        // 计算开始日期和结束日期之间的天数差
        long daysBetween = DateUtil.betweenDay(beginDate, endDate, true);

        // 创建一个数组来存储这些日期
        String[] dateRange = new String[(int) daysBetween];

        // 填充数组
        for (int i = 0; i < daysBetween; i++) {
            DateTime date = DateUtil.offsetDay(beginDate, i);
            dateRange[i] = DateUtil.format(date, "yyyy-MM-dd");
        }

        if (studentsConsumptionRecordsList.isEmpty()) {
            StudentsConsumeDTO studentsConsumeDTO = new StudentsConsumeDTO();
            studentsConsumeDTO.setTime(dateRange);
            return studentsConsumeDTO;
        } else {
            // 转成JSON格式
            JSONArray recordsArray = new JSONArray();
            for (StudentsConsumptionRecords record : studentsConsumptionRecordsList) {
                JSONObject recordJson = new JSONObject();
                recordJson.put("XH", record.getXH());
                recordJson.put("XFDD", record.getXFDD());
                recordJson.put("XFJE", record.getXFJE());
                recordJson.put("XFSJ", record.getXFSJ());
                recordJson.put("ID", record.getID());
                recordsArray.put(recordJson);
            }
            JSONArray api = PythonAPIClient.API(recordsArray, "sum");   // api 是python的接口
            // Convert the first JSONArray (strings) to String[]
            JSONArray stringArray = api.getJSONArray(0);
            String[] dates = new String[stringArray.length()];
            for (int i = 0; i < stringArray.length(); i++) {
                dates[i] = stringArray.getString(i);
            }

            JSONArray stringArray1 = api.getJSONArray(1);
            String[] dates1 = new String[stringArray1.length()];
            for (int i = 0; i < stringArray1.length(); i++) {
                dates1[i] = stringArray1.getString(i);
                System.out.println(dates1);
            }

            // 解决天数问题
            int i = 0, j = 0;
            int n = dateRange.length;
            String[] aver = new String[dateRange.length];
            while (i < n) {
                if (dateRange[i].equals(dates[j])) {
                    aver[i] = dates1[j];
                    if (j < dates.length - 1) {
                        j++;
                    }
                } else {
                    aver[i] = "0";
                }
                i++;
            }

            if (studentsConsumptionRecordsList1.isEmpty()) {
                StudentsConsumeDTO studentsConsumeDTO = new StudentsConsumeDTO();
                studentsConsumeDTO.setTime(dateRange);
                studentsConsumeDTO.setAver(aver);
                return studentsConsumeDTO;
            } else {
                // 转成JSON格式
                JSONArray recordsArray1 = new JSONArray();
                for (StudentsConsumptionRecords record : studentsConsumptionRecordsList1) {
                    JSONObject recordJson1 = new JSONObject();
                    recordJson1.put("XH", record.getXH());
                    recordJson1.put("XFDD", record.getXFDD());
                    recordJson1.put("XFJE", record.getXFJE());
                    recordJson1.put("XFSJ", record.getXFSJ());
                    recordJson1.put("ID", record.getID());
                    recordsArray1.put(recordJson1);
                }
                JSONArray api1 = PythonAPIClient.API(recordsArray1, "predict");   // api 是python的接口
                System.out.println(api1);
                // Convert the first JSONArray (strings) to String[]
                JSONArray stringArray2 = api1.getJSONArray(0);
                String[] dat = new String[stringArray2.length()];
                for (int k = 0; k < stringArray2.length(); k++) {
                    dat[k] = stringArray2.getString(k);
                }

                JSONArray stringArray3 = api1.getJSONArray(1);
                String[] dat1 = new String[stringArray3.length()];
                for (int k = 0; k < stringArray3.length(); k++) {
                    dat1[k] = stringArray3.getString(k);
                }

                JSONArray stringArray4 = api1.getJSONArray(2);
                String[] dat2 = new String[stringArray4.length()];
                for (int k = 0; k < stringArray4.length(); k++) {
                    dat2[k] = stringArray4.getString(k);
                }

                JSONArray stringArray5 = api1.getJSONArray(3);
                String[] dat3 = new String[stringArray5.length()];
                for (int k = 0; k < stringArray5.length(); k++) {
                    dat3[k] = stringArray5.getString(k);
                }

                JSONArray stringArray6 = api1.getJSONArray(4);
                String[] dat4 = new String[stringArray6.length()];
                for (int k = 0; k < stringArray6.length(); k++) {
                    dat4[k] = stringArray6.getString(k);
                }


                // 解决天数问题
                int l = 0, m = 0;
                int len = dateRange.length;
                String[] zao = new String[dateRange.length];
                String[] zhong = new String[dateRange.length];
                String[] wan = new String[dateRange.length];
                while (l < len) {
                    if (dateRange[l].equals(dat[m])) {
                        zao[l] = (dat1[m]);
                        zhong[l] = (dat2[m]);
                        wan[l] = (dat3[m]);
                        if (m < dat.length - 1) {
                            m++;
                        }
                    } else {
                        zao[l] = "0";
                        zhong[l] = "0";
                        wan[l] = "0";
                    }
                    l++;
                }

                StudentsConsumeDTO studentsConsumeDTO = new StudentsConsumeDTO();
                studentsConsumeDTO.setTime(dateRange);
                studentsConsumeDTO.setZao(zao);
                studentsConsumeDTO.setZhong(zhong);
                studentsConsumeDTO.setWan(wan);
                studentsConsumeDTO.setPoor(dat4);
                studentsConsumeDTO.calculateSum();
                studentsConsumeDTO.setAver(aver);

                return studentsConsumeDTO;
            }
        }
    }

    @SneakyThrows
    @Override
    public StudentsConsumeDTO selectByWeek(StudentsConsumptionRecordsVO studentsConsumptionRecordsVO) {
        List<StudentsConsumptionRecords> studentsConsumptionRecordsList = studentsConsumptionRecordsMapper.selectByWeek(studentsConsumptionRecordsVO);
        String today = DateUtil.today();
        Date date = DateUtil.parse(today);
        String lastWeek[] = new String[7];
        DateTime last1 = DateUtil.offsetDay(date, -1);
        lastWeek[6] = DateUtil.format(last1, "yyyy-MM-dd");
        DateTime last2 = DateUtil.offsetDay(date, -2);
        lastWeek[5] = DateUtil.format(last2, "yyyy-MM-dd");
        DateTime last3 = DateUtil.offsetDay(date, -3);
        lastWeek[4] = DateUtil.format(last3, "yyyy-MM-dd");
        DateTime last4 = DateUtil.offsetDay(date, -4);
        lastWeek[3] = DateUtil.format(last4, "yyyy-MM-dd");
        DateTime last5 = DateUtil.offsetDay(date, -5);
        lastWeek[2] = DateUtil.format(last5, "yyyy-MM-dd");
        DateTime last6 = DateUtil.offsetDay(date, -6);
        lastWeek[1] = DateUtil.format(last6, "yyyy-MM-dd");
        DateTime last7 = DateUtil.offsetDay(date, -7);
        lastWeek[0] = DateUtil.format(last7, "yyyy-MM-dd");
        if (studentsConsumptionRecordsList.isEmpty()) {
            StudentsConsumeDTO studentsConsumeDTO = new StudentsConsumeDTO();
            studentsConsumeDTO.setTime(lastWeek);
            return studentsConsumeDTO;
        } else {
            // 转成JSON格式
            JSONArray recordsArray = new JSONArray();
            for (StudentsConsumptionRecords record : studentsConsumptionRecordsList) {
                JSONObject recordJson = new JSONObject();
                recordJson.put("XH", record.getXH());
                recordJson.put("XFDD", record.getXFDD());
                recordJson.put("XFJE", record.getXFJE());
                recordJson.put("XFSJ", record.getXFSJ());
                recordJson.put("ID", record.getID());
                recordsArray.put(recordJson);
            }
            JSONArray api = PythonAPIClient.API(recordsArray, "predict");   // api 是python的接口
            // Convert the first JSONArray (strings) to String[]
            JSONArray stringArray = api.getJSONArray(0);
            String[] dates = new String[stringArray.length()];
            for (int i = 0; i < stringArray.length(); i++) {
                dates[i] = stringArray.getString(i);
            }

            JSONArray stringArray1 = api.getJSONArray(1);
            String[] dates1 = new String[stringArray1.length()];
            for (int i = 0; i < stringArray1.length(); i++) {
                dates1[i] = stringArray1.getString(i);
            }
            JSONArray stringArray2 = api.getJSONArray(2);
            String[] dates2 = new String[stringArray2.length()];
            for (int i = 0; i < stringArray2.length(); i++) {
                dates2[i] = stringArray2.getString(i);
            }
            JSONArray stringArray3 = api.getJSONArray(3);
            String[] dates3 = new String[stringArray3.length()];
            for (int i = 0; i < stringArray3.length(); i++) {
                dates3[i] = stringArray3.getString(i);
            }
            JSONArray stringArray4 = api.getJSONArray(4);
            String[] dates4 = new String[stringArray4.length()];
            for (int i = 0; i < stringArray4.length(); i++) {
                dates4[i] = stringArray4.getString(i);
            }
            // 解决天数问题
            int i = 0, j = 0;
            int n = lastWeek.length;
            String[] zao = new String[7];
            String[] zhong = new String[7];
            String[] wan = new String[7];
            while (i < n) {
                if (lastWeek[i].equals(dates[j])) {
                    zao[i] = dates1[j];
                    zhong[i] = (dates2[j]);
                    wan[i] = (dates3[j]);
                    if (j < dates.length - 1) {
                        j++;
                    }
                } else {
                    zao[i] = "0";
                    zhong[i] = "0";
                    wan[i] = "0";
                }
                i++;
            }


            StudentsConsumeDTO studentsConsumeDTO = new StudentsConsumeDTO();
            studentsConsumeDTO.setTime(lastWeek);
            studentsConsumeDTO.setZao(zao);
            studentsConsumeDTO.setZhong(zhong);
            studentsConsumeDTO.setWan(wan);
            studentsConsumeDTO.setPoor(dates4);
            studentsConsumeDTO.calculateSum();


            return studentsConsumeDTO;
        }
    }

    @SneakyThrows
    @Override
    public StudentsConsumeDTO selectByWeekAll(StudentsConsumptionRecordsVO studentsConsumptionRecordsVO) {
        List<StudentsConsumptionRecords> studentsConsumptionRecordsList = studentsConsumptionRecordsMapper.selectByWeekAll(studentsConsumptionRecordsVO);
        List<StudentsConsumptionRecords> studentsConsumptionRecordsList1 = studentsConsumptionRecordsMapper.selectByWeek(studentsConsumptionRecordsVO);
        String today = DateUtil.today();
        Date date = DateUtil.parse(today);
        String lastWeek[] = new String[7];
        DateTime last1 = DateUtil.offsetDay(date, -1);
        lastWeek[6] = DateUtil.format(last1, "yyyy-MM-dd");
        DateTime last2 = DateUtil.offsetDay(date, -2);
        lastWeek[5] = DateUtil.format(last2, "yyyy-MM-dd");
        DateTime last3 = DateUtil.offsetDay(date, -3);
        lastWeek[4] = DateUtil.format(last3, "yyyy-MM-dd");
        DateTime last4 = DateUtil.offsetDay(date, -4);
        lastWeek[3] = DateUtil.format(last4, "yyyy-MM-dd");
        DateTime last5 = DateUtil.offsetDay(date, -5);
        lastWeek[2] = DateUtil.format(last5, "yyyy-MM-dd");
        DateTime last6 = DateUtil.offsetDay(date, -6);
        lastWeek[1] = DateUtil.format(last6, "yyyy-MM-dd");
        DateTime last7 = DateUtil.offsetDay(date, -7);
        lastWeek[0] = DateUtil.format(last7, "yyyy-MM-dd");
        if (studentsConsumptionRecordsList.isEmpty()) {
            int f = 0;
            for (String da : lastWeek) {
                int startIndex = da.indexOf("-") + 1; // 找到第一个连字符后的位置
                int endIndex = da.indexOf("-", startIndex); // 找到第二个连字符后的位置
                String monthDay = da.substring(startIndex, endIndex + 3); // 截取月份和日期部分
                lastWeek[f++] = monthDay;
            }
            StudentsConsumeDTO studentsConsumeDTO = new StudentsConsumeDTO();
            studentsConsumeDTO.setTime(lastWeek);
            return studentsConsumeDTO;
        } else {
            // 转成JSON格式
            JSONArray recordsArray = new JSONArray();
            for (StudentsConsumptionRecords record : studentsConsumptionRecordsList) {
                JSONObject recordJson = new JSONObject();
                recordJson.put("XH", record.getXH());
                recordJson.put("XFDD", record.getXFDD());
                recordJson.put("XFJE", record.getXFJE());
                recordJson.put("XFSJ", record.getXFSJ());
                recordJson.put("ID", record.getID());
                recordsArray.put(recordJson);
            }
            JSONArray api = PythonAPIClient.API(recordsArray, "sum");   // api 是python的接口
            System.out.println(api);
            // Convert the first JSONArray (strings) to String[]
            JSONArray stringArray = api.getJSONArray(0);
            String[] dates = new String[stringArray.length()];
            for (int i = 0; i < stringArray.length(); i++) {
                dates[i] = stringArray.getString(i);
            }

            JSONArray stringArray1 = api.getJSONArray(1);
            String[] dates1 = new String[stringArray1.length()];
            for (int i = 0; i < stringArray1.length(); i++) {
                dates1[i] = stringArray1.getString(i);
                // 将字符串转换为double以进行格式化
                double number = Double.parseDouble(dates1[i]);
                // 使用String.format保留两位小数
                String formattedNumber = String.format("%.2f", number);
                dates1[i] = formattedNumber;
            }

            // 解决天数问题
            int i = 0, j = 0;
            int n = lastWeek.length;
            String[] aver = new String[7];
            while (i < n) {
                if (lastWeek[i].equals(dates[j])) {
                    aver[i] = dates1[j];
                    if (j < dates.length - 1) {
                        j++;
                    }
                } else {
                    aver[i] = "0";
                }
                i++;
            }

            if (studentsConsumptionRecordsList1.isEmpty()) {
                int f = 0;
                for (String da : lastWeek) {
                    int startIndex = da.indexOf("-") + 1; // 找到第一个连字符后的位置
                    int endIndex = da.indexOf("-", startIndex); // 找到第二个连字符后的位置
                    String monthDay = da.substring(startIndex, endIndex + 3); // 截取月份和日期部分
                    lastWeek[f++] = monthDay;
                }
                StudentsConsumeDTO studentsConsumeDTO = new StudentsConsumeDTO();
                studentsConsumeDTO.setTime(lastWeek);
                studentsConsumeDTO.setAver(aver);
                return studentsConsumeDTO;
            } else {
                // 转成JSON格式
                JSONArray recordsArray1 = new JSONArray();
                for (StudentsConsumptionRecords record : studentsConsumptionRecordsList1) {
                    JSONObject recordJson1 = new JSONObject();
                    recordJson1.put("XH", record.getXH());
                    recordJson1.put("XFDD", record.getXFDD());
                    recordJson1.put("XFJE", record.getXFJE());
                    recordJson1.put("XFSJ", record.getXFSJ());
                    recordJson1.put("ID", record.getID());
                    recordsArray1.put(recordJson1);
                }
                JSONArray api1 = PythonAPIClient.API(recordsArray1, "predict");   // api 是python的接口
                // Convert the first JSONArray (strings) to String[]
                JSONArray stringArray2 = api1.getJSONArray(0);
                String[] dat = new String[stringArray2.length()];
                for (int k = 0; k < stringArray2.length(); k++) {
                    dat[k] = stringArray2.getString(k);
                }

                JSONArray stringArray3 = api1.getJSONArray(1);
                String[] dat1 = new String[stringArray3.length()];
                for (int k = 0; k < stringArray3.length(); k++) {
                    dat1[k] = stringArray3.getString(k);
                }

                JSONArray stringArray4 = api1.getJSONArray(2);
                String[] dat2 = new String[stringArray4.length()];
                for (int k = 0; k < stringArray4.length(); k++) {
                    dat2[k] = stringArray4.getString(k);
                }

                JSONArray stringArray5 = api1.getJSONArray(3);
                String[] dat3 = new String[stringArray5.length()];
                for (int k = 0; k < stringArray5.length(); k++) {
                    dat3[k] = stringArray5.getString(k);
                }

                JSONArray stringArray6 = api1.getJSONArray(4);
                String[] dat4 = new String[stringArray6.length()];
                for (int k = 0; k < stringArray6.length(); k++) {
                    dat4[k] = stringArray6.getString(k);
                }


                // 解决天数问题
                int l = 0, m = 0;
                int len = lastWeek.length;
                String[] zao = new String[7];
                String[] zhong = new String[7];
                String[] wan = new String[7];
                while (l < len) {
                    if (lastWeek[l].equals(dat[m])) {
                        zao[l] = (dat1[m]);
                        zhong[l] = (dat2[m]);
                        wan[l] = (dat3[m]);
                        if (m < dat.length - 1) {
                            m++;
                        }
                    } else {
                        zao[l] = "0";
                        zhong[l] = "0";
                        wan[l] = "0";
                    }
                    l++;
                }

                int f = 0;
                for (String da : lastWeek) {
                    int startIndex = da.indexOf("-") + 1; // 找到第一个连字符后的位置
                    int endIndex = da.indexOf("-", startIndex); // 找到第二个连字符后的位置
                    String monthDay = da.substring(startIndex, endIndex + 3); // 截取月份和日期部分
                    lastWeek[f++] = monthDay;
                }


                StudentsConsumeDTO studentsConsumeDTO = new StudentsConsumeDTO();
                studentsConsumeDTO.setTime(lastWeek);
                studentsConsumeDTO.setZao(zao);
                studentsConsumeDTO.setZhong(zhong);
                studentsConsumeDTO.setWan(wan);
                studentsConsumeDTO.setPoor(dat4);
                studentsConsumeDTO.calculateSum();
                studentsConsumeDTO.setAver(aver);

                return studentsConsumeDTO;
            }

        }
    }

}
