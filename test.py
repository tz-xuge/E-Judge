# -- coding:utf-8 --
import pandas as pd
import pickle
from sklearn.model_selection import train_test_split

# 读取 output.csv 文件
data = pd.read_csv('output.csv')

# 从 data 中随机选择 10% 作为测试集
x_train, x_test, y_train, y_test = train_test_split(
    data.drop('label', axis=1),  # 特征
    data['label'],  # 目标变量
    test_size=0.1,  # 测试集占 10%
    random_state=42  # 设置随机种子确保结果可复现
)
# 加载模型
with open('random_forest_model.pkl', 'rb') as f:
    loaded_model = pickle.load(f)

# 使用加载的模型进行分类预测
y_pred = loaded_model.predict(x_test)

# 创建一个包含实际标签和预测标签的数据框
result = pd.DataFrame({
    'Actual': y_test,
    'Predicted': y_pred
})

# 统计预测为1和预测为0的数量
num_pred_1 = (y_pred == 1).sum()
num_pred_0 = (y_pred == 0).sum()

print(f"Number of predictions as 1: {num_pred_1}")
print(f"Number of predictions as 0: {num_pred_0}")

# 打印被预测为1的样本的特征
print("\nFeatures of samples predicted as 1:")
print(x_test[y_pred == 1])
print("\nFeatures of samples predicted as 0:")
print(x_test[y_pred == 0])

# 查看前 10 行结果
print(result.head(10))