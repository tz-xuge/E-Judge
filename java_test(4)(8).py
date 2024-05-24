# -- coding:utf-8 --
# from flask import Flask, request, jsonify
# import pickle
# from sklearn.model_selection import train_test_split
#
# app = Flask(__name__)
#
# # 加载模型
# with open('random_forest_model.pkl', 'rb') as f:
#     model = pickle.load(f)
#
# @app.route('/predict', methods=['POST'])
# def predict():
#     data = request.get_json()
#     features = [data['zao'], data['zhong'], data['wan'], data['times']]
#     XH = data['XH']
#     prediction = model.predict([features]).tolist()
#     return jsonify({'XH': XH,'prediction': prediction})
#
# if __name__ == '__main__':
#     app.run(host='0.0.0.0', port=5001)
import numpy as np
from flask import Flask, request, jsonify
import pickle
from sklearn.model_selection import train_test_split
import json
import pandas as pd
from datetime import date
import math

app = Flask(__name__)

# 加载模型
with open('random_forest_model.pkl', 'rb') as f:
    model = pickle.load(f)

def classify_time(x):
    if x.time() >= pd.Timestamp('05:00:00').time() and x.time() <= pd.Timestamp('11:00:00').time():
        return 'zao'
    elif x.time() > pd.Timestamp('11:00:00').time() and x.time() <= pd.Timestamp('16:00:00').time():
        return 'zhong'
    else:
        return 'wan'

def date_serializer(obj):
    if isinstance(obj, date):
        return obj.isoformat()
    else:
        return obj

@app.route('/predict', methods=['POST'])
def predict():
    data = request.get_json()
    data = pd.DataFrame(data)
    print(data)
    data_test = data_cl(data)
    print(data_test)
    print(len(data_test))
    # 将 JSON 数据转换为 DataFrame
    df = pd.DataFrame(data_test)
    data_dict = {
        'XFSJ':  df['XFSJ'].tolist(),
        'zao': df['zao'].tolist(),
        'zhong': df['zhong'].tolist(),
        'wan': df['wan'].tolist()
    }
    print(data_dict)


    # 计算每个学号下 zao、zhong、wan 的平均值
    result = df.groupby('XH')[['zao', 'zhong', 'wan']].apply(lambda x: pd.Series({
        'zao': x[x['zao'] != 0]['zao'].mean() if x[x['zao'] != 0]['zao'].any() else 0,
        'zhong': x[x['zhong'] != 0]['zhong'].mean() if x[x['zhong'] != 0]['zhong'].any() else 0,
        'wan': x[x['wan'] != 0]['wan'].mean() if x[x['wan'] != 0]['wan'].any() else 0
    })).reset_index()

    # 计算每个学号下 zao、zhong、wan 非零值出现的次数
    times_df = df.groupby('XH')[['zao', 'zhong', 'wan']].apply(lambda x: pd.Series({
        'zao_times': len(x[x['zao'] != 0]),
        'zhong_times': len(x[x['zhong'] != 0]),
        'wan_times': len(x[x['wan'] != 0])
    })).reset_index()

    # 将 times_df 与 result 合并
    final_result = pd.merge(result, times_df, on='XH', how='left')

    # 添加 'times' 列
    final_result['times'] = final_result[['zao_times', 'zhong_times', 'wan_times']].sum(axis=1)

    final_result = final_result[['XH', 'zao', 'zhong', 'wan', 'times']]

    # 输出结果
    print(final_result)

    results = []
    # for item in final_result:
    #     features = [item['zao'], item['zhong'], item['wan'], item['times']]
    #     XH = item['XH']
    #     prediction = model.predict([features])[0]
    #     results.append({'XH': XH, 'zao': item['zao'], 'zhong': item['zhong'], 'wan': item['wan'], 'times': item['times'], 'prediction': prediction.tolist()})
    for index, row in final_result.iterrows():
        features = [row['zao'], row['zhong'], row['wan'], row['times']]
        XH = row['XH']
        # prediction = model.predict([features])[0]
        # 将 features 转换为 DataFrame 格式
        features_df = pd.DataFrame([features], columns=['zao', 'zhong', 'wan', 'times'])
        # prediction = model.predict([features_df])[0]
        prediction = model.predict(features_df)
        # results.append({'XH': XH, 'zao': row['zao'], 'zhong': row['zhong'], 'wan': row['wan'], 'times': row['times'],
        #                 'prediction': prediction.tolist()})
        print(type(prediction))
        data_dict.setdefault('prediction', []).append(int(prediction))
        json_data = json.dumps(list(data_dict.values()), default=date_serializer)
        print(json_data)
    return json_data

@app.route('/predict2', methods=['POST'])
def prediction2():
    data = request.get_json()
    data = pd.DataFrame(data)
    print(data)
    data_test = data_cl(data)
    print(data_test)
    print(len(data_test))
    # 计算 week_times 向上取整为整数
    week_times = math.floor(len(data_test) / 7)
    if week_times == 0 : week_times = 1
    print('week_times:', week_times)

    # 将 JSON 数据转换为 DataFrame
    df = pd.DataFrame(data_test)
    data_dict = {
        'XFSJ': df['XFSJ'].tolist(),
        'zao': df['zao'].tolist(),
        'zhong': df['zhong'].tolist(),
        'wan': df['wan'].tolist()
    }
    print(data_dict)
    # 计算每个学号下 zao、zhong、wan 的平均值
    # result = df.groupby('XH')[['zao', 'zhong', 'wan']].apply(lambda x: pd.Series({
    #     'zao': x[x['zao'] != 0]['zao'].mean().round(2),
    #     'zhong': x[x['zhong'] != 0]['zhong'].mean().round(2),
    #     'wan': x[x['wan'] != 0]['wan'].mean().round(2)
    # })).reset_index()
    result = df.groupby('XH')[['zao', 'zhong', 'wan']].apply(lambda x: pd.Series({
        'zao': x[x['zao'] != 0]['zao'].mean() if x[x['zao'] != 0]['zao'].any() else 0,
        'zhong': x[x['zhong'] != 0]['zhong'].mean() if x[x['zhong'] != 0]['zhong'].any() else 0,
        'wan': x[x['wan'] != 0]['wan'].mean() if x[x['wan'] != 0]['wan'].any() else 0
    })).reset_index()

    # 计算每个学号下 zao、zhong、wan 非零值出现的次数
    times_df = df.groupby('XH')[['zao', 'zhong', 'wan']].apply(lambda x: pd.Series({
        'zao_times': len(x[x['zao'] != 0]),
        'zhong_times': len(x[x['zhong'] != 0]),
        'wan_times': len(x[x['wan'] != 0])
    })).reset_index()
    # times_df = times_df.astype(int)
    # print(times_df)
    # times_df /= week_times
    # times_df = times_df.astype(str)

    # 将 times_df 与 result 合并
    final_result = pd.merge(result, times_df, on='XH', how='left')

    # 添加 'times' 列
    final_result['times'] = final_result[['zao_times', 'zhong_times', 'wan_times']].sum(axis=1)

    final_result = final_result[['XH', 'zao', 'zhong', 'wan', 'times']]
    # 将 'times' 列的数据类型转换为数值型
    final_result['times'] = final_result['times'].astype(np.int64)
    # 将 'times' 列的值除以周数
    final_result['times'] = final_result['times'] / week_times

    # 输出结果
    print(final_result)

    for index, row in final_result.iterrows():
        features = [row['zao'], row['zhong'], row['wan'], row['times']]
        XH = row['XH']
        # prediction = model.predict([features])[0]
        # 将 features 转换为 DataFrame 格式
        features_df = pd.DataFrame([features], columns=['zao', 'zhong', 'wan', 'times'])
        # prediction = model.predict([features_df])[0]
        prediction = model.predict(features_df)
        print(type(prediction))
        data_dict.setdefault('prediction', []).append(int(prediction))
        json_data = json.dumps(list(data_dict.values()), default=date_serializer)
        print(json_data)
    return json_data

@app.route('/all_predict', methods=['POST'])
def all_predict():
    data = request.get_json()
    data = pd.DataFrame(data)
    data_test = data_cl(data)

    # 将 JSON 数据转换为 DataFrame
    df = pd.DataFrame(data_test)

    # 按 'XH' 分组并取最后 7 条数据
    df_week_one = df.groupby('XH').tail(7)
    print(df_week_one)
    # # 统计每个学号对应的行数
    # row_counts = df.groupby('XH').size()
    # # 计算 week_times 并向下取整为整数
    # week_times = (row_counts / 7).apply(math.floor).astype(int)
    # # 判断周数是否为 0,如果为 0 则赋值为 1
    # week_times = week_times.replace(0, 1)
    # # 将week_times转换为DataFrame格式
    # week_times_df = pd.DataFrame({'XH': week_times.index, 'week_times': week_times.values})
    data_dict = {
        'XH': [],
    }
    print(data_dict)

    # 计算每个学号下 zao、zhong、wan 的平均值
    result = df_week_one.groupby('XH')[['zao', 'zhong', 'wan']].apply(lambda x: pd.Series({
        'zao': x[x['zao'] != 0]['zao'].mean() if x[x['zao'] != 0]['zao'].any() else 0,
        'zhong': x[x['zhong'] != 0]['zhong'].mean() if x[x['zhong'] != 0]['zhong'].any() else 0,
        'wan': x[x['wan'] != 0]['wan'].mean() if x[x['wan'] != 0]['wan'].any() else 0
    })).reset_index()

    # 计算每个学号下 zao、zhong、wan 非零值出现的次数
    times_df = df_week_one.groupby('XH')[['zao', 'zhong', 'wan']].apply(lambda x: pd.Series({
        'zao_times': len(x[x['zao'] != 0]),
        'zhong_times': len(x[x['zhong'] != 0]),
        'wan_times': len(x[x['wan'] != 0])
    })).reset_index()

    # 将 times_df 与 result 合并
    final_result = pd.merge(result, times_df, on='XH', how='left')

    # 添加 'times' 列
    final_result['times'] = final_result[['zao_times', 'zhong_times', 'wan_times']].sum(axis=1)

    # 只保留 'times' 列
    final_result = final_result[['XH', 'zao', 'zhong', 'wan', 'times']]

    # # 将 'times' 列的数据类型转换为数值型
    # final_result['times'] = final_result['times'].astype(np.int64)
    # # 将 'times' 列的值除以周数
    # final_result['times'] = final_result['times'] / week_times

    # 合并两个DataFrame
    # s_final_result = pd.merge(final_result, week_times_df, on='XH', how='left')

    # 计算新的times列
    # s_final_result['times'] = s_final_result['times'] / s_final_result['week_times']
    # s_final_result['times'] = s_final_result['times'].round().astype(int)


    # 输出结果
    print('final_result:', final_result)

    for index, row in final_result.iterrows():
        features = [row['zao'], row['zhong'], row['wan'], row['times']]
        XH = row['XH']
        # 将 features 转换为 DataFrame 格式
        features_df = pd.DataFrame([features], columns=['zao', 'zhong', 'wan', 'times'])
        # prediction = model.predict([features_df])[0]
        prediction = model.predict(features_df)
        # results.append({'XH': XH, 'zao': row['zao'], 'zhong': row['zhong'], 'wan': row['wan'], 'times': row['times'],
        #                 'prediction': prediction.tolist()})
        # print(type(prediction))
        if prediction == 1:
            data_dict['XH'].append(XH)
        # data_dict.setdefault('prediction', []).append(int(prediction))
        json_data = json.dumps(list(data_dict.values()))
    print(json_data)
    return json_data


@app.route('/sum', methods=['POST'])
def sum():
    data = request.get_json()
    data = pd.DataFrame(data)

    data['XFSJ'] = pd.to_datetime(data['XFSJ'])

    data['XFJE'] = pd.to_numeric(data['XFJE'], errors='coerce')


    data = data.drop(columns=['XFDD', 'ID'])

    data['XFSJ'] = pd.to_datetime(data['XFSJ']).dt.date

    total_students = data['XH'].nunique()

    # 计算每天的总消费金额
    grouped = data.groupby('XFSJ')['XFJE'].sum()

    # 计算每个学生每天的平均消费金额
    average_consumption = grouped / total_students
    grouped.index = pd.to_datetime(grouped.index)
    # 准备输出
    dates = grouped.index.strftime('%Y-%m-%d').tolist()
    average_consumption = average_consumption.tolist()

    output = [dates, average_consumption]

    return output

# def data_cl(data):
#     data['XFSJ'] = pd.to_datetime(data['XFSJ'])
#     data['时间段'] = data['XFSJ'].apply(classify_time)
#     data['XFJE'] = pd.to_numeric(data['3XFJE'], errors='coerce')
#     data['zao'] = 0
#     data['zhong'] = 0
#     data['wan'] = 0
#
#
#     data.loc[data['时间段'] == 'zao', 'zao'] = data['XFJE']
#     data.loc[data['时间段'] == 'zhong', 'zhong'] = data['XFJE']
#     data.loc[data['时间段'] == 'wan', 'wan'] = data['XFJE']
#
#
#     data = data.drop(columns=['XFJE', '时间段', 'XFDD', 'ID'])
#
#     data['XFSJ'] = pd.to_datetime(data['XFSJ']).dt.date
#
#     data = data.groupby(['XH', 'XFSJ'], as_index=False).sum()
#
#     return data


import pandas as pd

def data_cl(data):
    # 如果 data 是 JSON 格式,先转换为 DataFrame
    if isinstance(data, dict):
        data = pd.DataFrame.from_dict(data, orient='columns')

    data['XFSJ'] = pd.to_datetime(data['XFSJ'])
    data['时间段'] = data['XFSJ'].apply(classify_time)
    data['XFJE'] = pd.to_numeric(data['XFJE'], errors='coerce')
    data['zao'] = 0
    data['zhong'] = 0
    data['wan'] = 0

    data.loc[data['时间段'] == 'zao', 'zao'] = data['XFJE']
    data.loc[data['时间段'] == 'zhong', 'zhong'] = data['XFJE']
    data.loc[data['时间段'] == 'wan', 'wan'] = data['XFJE']

    data = data.drop(columns=['XFJE', '时间段', 'XFDD', 'ID'])

    data['XFSJ'] = pd.to_datetime(data['XFSJ']).dt.date

    data = data.groupby(['XH', 'XFSJ'], as_index=False).sum()

    return data



if __name__ == '__main__':
    app.run(host='0.0.0.0', port=5001)