package com.hebut.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.json.JSONArray;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class StudentsConsumeDTO {

    private String[] time;
    private String[] zao;
    private String[] zhong;
    private String[] wan;
    private String[] poor;
    private String[] sum1;
    private String[] aver;


    // 计算 zao zhong wan 的和
    public void calculateSum() {
        if (zao == null || zhong == null || wan == null) {
            throw new IllegalArgumentException("zao, zhong, and wan arrays must not be null");
        }
        int length = Math.min(Math.min(zao.length, zhong.length), wan.length);
        sum1 = new String[length];
        for (int i = 0; i < length; i++) {
            try {
                double zaoValue = (zao[i] != null && !zao[i].isEmpty()) ? Double.parseDouble(zao[i]) : 0.0;
                double zhongValue = (zhong[i] != null && !zhong[i].isEmpty()) ? Double.parseDouble(zhong[i]) : 0.0;
                double wanValue = (wan[i] != null && !wan[i].isEmpty()) ? Double.parseDouble(wan[i]) : 0.0;
                sum1[i] = String.format("%.2f", zaoValue + zhongValue + wanValue);
            } catch (NumberFormatException e) {
                // 如果遇到无法解析为数字的字符串，可以选择抛出异常或设置一个默认值
                sum1[i] = "0.0"; // 或者处理为其他合适的默认值
                System.err.println("Error parsing double value at index " + i + ": " + e.getMessage());
            }
        }
    }
}
