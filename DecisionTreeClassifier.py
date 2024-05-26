# -- coding:utf-8 --
# Python随机森林分类
import pandas as pd
import pickle
import numpy as np
import matplotlib.pyplot as plt
import seaborn as sns
from sklearn.model_selection import train_test_split, learning_curve
from sklearn.ensemble import RandomForestClassifier
from sklearn.preprocessing import StandardScaler
from imblearn.over_sampling import SMOTE
from imblearn.under_sampling import RandomUnderSampler
from sklearn.tree import DecisionTreeClassifier
from sklearn.metrics import confusion_matrix, classification_report, accuracy_score, roc_curve, auc




path = 'output.csv' #数据路径
rawdata = pd.read_csv(path)  #加载数据
scaler = StandardScaler()
numerical_cols = ['zao', 'zhong', 'wan', 'times']
rawdata[numerical_cols] = scaler.fit_transform(rawdata[numerical_cols])
print(rawdata)

x = rawdata.drop('label',axis = 1) #输入特征
y = rawdata['label']  #目标变量
x_train,x_test,y_train,y_test = train_test_split(x,y,test_size = 0.3,random_state=42) #30%为测试集，则70%为训练集
# 应用 SMOTE 和随机欠采样
smote = SMOTE(random_state=42)
rus = RandomUnderSampler(random_state=42)
x_train_resampled, y_train_resampled = smote.fit_resample(x_train, y_train)
x_train_balanced, y_train_balanced = rus.fit_resample(x_train_resampled, y_train_resampled)
# 输出正标签样本的数量
print(f"正标签样本数量: {np.sum(y_train_balanced == 1)}")
print(f"负标签样本数量: {np.sum(y_train_balanced == 0)}")
# 创建单棵决策树分类器
tree_clf = DecisionTreeClassifier(random_state=42)

# 训练模型
tree_clf.fit(x_train_balanced, y_train_balanced)
# 进行预测
y_pred = tree_clf.predict(x_test)
importances = tree_clf.feature_importances_ # 计算特征重要性


# 绘制特征重要性条形图
plt.figure(figsize=(10, 6))
plt.bar(range(len(importances)), importances)
plt.xticks(range(len(importances)), x.columns, rotation=90)
plt.title('Feature Importances')
plt.xlabel('Feature')
plt.ylabel('Importance')
plt.show()

# 绘制混淆矩阵热图
plt.figure(figsize=(8, 6))
cm = confusion_matrix(y_test, y_pred)
sns.heatmap(cm, annot=True, cmap='Blues', fmt='g')
plt.title('Confusion Matrix')
plt.xlabel('Predicted')
plt.ylabel('Actual')
plt.show()

# 绘制ROC曲线
fpr, tpr, thresholds = roc_curve(y_test, tree_clf.predict_proba(x_test)[:,1])
roc_auc = auc(fpr, tpr)
plt.figure(figsize=(8, 6))
plt.plot(fpr, tpr, color='darkorange', lw=2, label='ROC curve (area = %0.2f)' % roc_auc)
plt.plot([0, 1], [0, 1], color='navy', lw=2, linestyle='--')
plt.xlim([0.0, 1.0])
plt.ylim([0.0, 1.05])
plt.xlabel('False Positive Rate')
plt.ylabel('True Positive Rate')
plt.title('Receiver Operating Characteristic (ROC) Curve')
plt.legend(loc="lower right")
plt.show()

# 绘制学习曲线
train_sizes, train_scores, test_scores = learning_curve(tree_clf, x, y, cv=5)
plt.figure(figsize=(10, 6))
plt.plot(train_sizes, np.mean(train_scores, axis=1), 'o-', color="r", label="Training score")
plt.plot(train_sizes, np.mean(test_scores, axis=1), 'o-', color="g", label="Test score")
plt.xlabel("Training examples")
plt.ylabel("Score")
plt.title("Learning Curve")
plt.legend(loc="best")
plt.show()


print("Importances")
print(importances)
print("Confusion Matrix:")
print(confusion_matrix(y_test, y_pred))  # 输出混淆矩阵
print("Classification Report:")
print(classification_report(y_test, y_pred))  # 输出分类报告
print("Accuracy:") #输出精度
print(accuracy_score(y_test, y_pred))

# 保存模型
with open('random_forest_model.pkl', 'wb') as f:
    pickle.dump(tree_clf, f)

