# # -- coding:utf-8 --
# import csv
# import numpy as np
#
# import csv
# import numpy as np
#
# # 读取CSV文件
# data = []
# with open('xf3.csv', 'r') as file:
#     reader = csv.reader(file)
#     header = next(reader) # 读取第一行作为表头
#     for row in reader:
#         row_data = [float(x) for x in row[1:]] # 删除第一列
#         data.append(row_data)
#
# # 将数据分成7行一组
# grouped_data = [data[i:i+7] for i in range(0, len(data), 7)]
#
# # 对每组数据求和,然后求平均值,忽略0值
# averaged_data = []
# for group_data in grouped_data:
#     group_sum = [sum(col) for col in zip(*group_data)]
#     group_count = [sum(1 for x in col if x != 0) for col in zip(*group_data)]
#     group_avg = [round(s / c, 2) if c != 0 else 0 for s, c in zip(group_sum, group_count)]
#     averaged_data.append(group_avg)
#
# # 将结果写入新的CSV文件
# with open('output.csv', 'w', newline='') as file:
#     writer = csv.writer(file)
#     writer.writerow(header[1:]) # 写入表头,除去第一列
#     writer.writerows(averaged_data)
#
# # 对每列数据求平均值
# col_averaged_data = [round(np.mean([row[i] for row in averaged_data]), 2) for i in range(len(averaged_data[0]))]
#
# # 添加 label 列
# labels = []
# label_counts = {'0': 0, '1': 0}
# for row in averaged_data:
#     below_50_count = 0
#     below_70_count = 0
#     for i, col_avg in enumerate(col_averaged_data):
#         if row[i] < 0.5 * col_avg:
#             below_50_count += 1
#         if row[i] < 0.7 * col_avg:
#             below_70_count += 1
#     label = 1 if (below_50_count >= 2) or (below_70_count >= 3) else 0
#     labels.append(label)
#     label_counts[str(label)] += 1
#
# # 输出到 CSV 文件
# with open('output.csv', 'w', newline='') as file:
#     writer = csv.writer(file)
#     writer.writerow(header[1:] + ['label']) # 写入表头,包括 label 列
#     writer.writerows([row + [label] for row, label in zip(averaged_data, labels)])
#
# print("数据已输出到 output.csv 文件。")
# print(f"Label 0 的数量: {label_counts['0']}")
# print(f"Label 1 的数量: {label_counts['1']}")




# -- coding:utf-8 --
import csv
import numpy as np
from sklearn.neighbors import NearestNeighbors


# 读取CSV文件
data = []
with open('input.csv', 'r') as file:
    reader = csv.reader(file)
    header = next(reader)  # 读取第一行作为表头
    header = header[1:]  # 删除前两列的表头
    for row in reader:
        row_data = [float(x) for x in row[2:]]  # 删除前两列
        data.append(row_data)

# 将数据分成7行一组
grouped_data = [data[i:i+7] for i in range(0, len(data), 7)]

# 对每组数据求和,然后求平均值,忽略0值
averaged_data = []
times_data = []
for group_data in grouped_data:
    group_sum = [sum(col) for col in zip(*group_data)]
    group_count = [sum(1 for x in col if x != 0) for col in zip(*group_data)]
    group_avg = [round(s / (c if c != 0 else 1), 2) for s, c in zip(group_sum, group_count)]
    averaged_data.append(group_avg)
    times_data.append(sum(group_count))

# print('averaged_data', averaged_data)
# # 对 averaged_data 的每一列进行归一化
# averaged_data_min = np.min(averaged_data, axis=0)
# averaged_data_max = np.max(averaged_data, axis=0)
# averaged_data_norm = (averaged_data - averaged_data_min) / (averaged_data_max - averaged_data_min)
#
# # 对 times_data 的每一列进行归一化
# times_data_min = np.min(times_data, axis=0)
# times_data_max = np.max(times_data, axis=0)
# times_data_norm = (times_data - times_data_min) / (times_data_max - times_data_min)
#
# # 将 times_data_norm 转换成2维数组
# times_data_norm = np.expand_dims(times_data_norm, axis=1)
#
# # 合并 averaged_data_norm 和 times_data_norm
# merged_data_norm = np.hstack((averaged_data_norm, times_data_norm))
#
# print(averaged_data_norm.shape)
# print(times_data_norm.shape)
# print(merged_data_norm.shape)
# print('averaged_data_norm', averaged_data_norm)
# print('times_data_norm', times_data_norm)
# print('merged_data_norm', merged_data_norm)

#  设定阈值
# thresholds = [0.5, 0.5, 0.5, 0.5]

# 步骤3: 进行分类
# y_pred = (merged_data_norm >= thresholds).astype(int)
# print(y_pred)

# 步骤2: 进行分类
# y_pred = np.sum(merged_data_norm < 0.5, axis=1)
# labels = (y_pred >= np.sum(merged_data_norm >= 0.5, axis=1)).astype(int)
#
# # 将分类标签添加到原始数据的最后一列
# X_final = np.hstack((averaged_data, np.expand_dims(labels, axis=1)))
# print(X_final)
#
# # 输出标签为1的数量
# num_ones = np.sum(labels)
# print(f"\n标签为1的数量: {num_ones}")


# 对每列数据求平均值
col_averaged_data = [round(np.mean([row[i] for row in averaged_data]), 2) for i in range(len(averaged_data[0]))]
print('col_averaged_data', col_averaged_data)
#
# 添加 label 列
labels = []
label_counts = {'0': 0, '1': 0}
count = 0
sum=0
for row, times in zip(averaged_data, times_data):
    below_50_count = 0
    below_80_count = 0
    super_count = 0
    for i, col_avg in enumerate(col_averaged_data):
        if row[i] < 0.55 * col_avg:
            below_50_count += 1
        if row[i] < 0.75 * col_avg:
            below_80_count += 1
        if row[i] > col_avg:
            super_count += 1
    label = 1 if ((below_50_count >= 2) or (below_80_count == 3)) and (times >= 8) and super_count == 0 else 0
    count += 1
    sum += times
    labels.append(label)
    label_counts[str(label)] += 1



# print('avg_times', sum/count)

# 打印出 label 为 1 的行
print("Label 1 的行:")
for row, times, label in zip(averaged_data, times_data, labels):
    if label == 1:
        print(row, "times:", times)

# 将结果写入新的CSV文件
with open('output.csv', 'w', newline='') as file:
    writer = csv.writer(file)
    writer.writerow(header[1:] + ['times', 'label'])  # 写入表头,包括 'times' 和 'label' 列
    for row, times, label in zip(averaged_data, times_data, labels):
        writer.writerow(row + [times, label])

print("数据已输出到 output.csv 文件。")
print(f"Label 0 的数量: {label_counts['0']}")
print(f"Label 1 的数量: {label_counts['1']}")









