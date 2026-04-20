<template>
  <div class="result-card">
    <!-- 课表卡片 -->
    <div v-if="intent === 'STUDY_SCHEDULE'" class="card-purple">
      <div class="card-purple-header">
        <svg xmlns="http://www.w3.org/2000/svg" width="14" height="14" fill="currentColor" viewBox="0 0 16 16">
          <path d="M3.5 0a.5.5 0 0 1 .5.5V1h8V.5a.5.5 0 0 1 1 0V1h1a2 2 0 0 1 2 2v11a2 2 0 0 1-2 2H2a2 2 0 0 1-2-2V3a2 2 0 0 1 2-2h1V.5a.5.5 0 0 1 .5-.5zM1 4v10a1 1 0 0 0 1 1h12a1 1 0 0 0 1-1V4H1z"/>
        </svg>
        今日课表
      </div>
      <div class="card-purple-body">
        <table class="schedule-table">
          <thead>
            <tr>
              <th>课程名称</th>
              <th>上课时间</th>
              <th>上课地点</th>
            </tr>
          </thead>
          <tbody>
            <tr v-for="(item, index) in data" :key="index">
              <td>{{ item.courseName }}</td>
              <td>{{ item.time }}</td>
              <td>{{ item.place }}</td>
            </tr>
            <tr v-if="data.length === 0">
              <td colspan="3" class="text-muted text-center">今日无课，好好休息～</td>
            </tr>
          </tbody>
        </table>
      </div>
    </div>
	
    <!-- 生活服务/资讯卡片 -->
    <div v-if="data && (intent === 'LIFE_SERVICE' || intent === 'CAMPUS_INFO')" class="card-purple card-green">
      <div class="card-purple-header">
        <svg xmlns="http://www.w3.org/2000/svg" width="14" height="14" fill="currentColor" viewBox="0 0 16 16">
          <path d="M8 16A8 8 0 1 0 8 0a8 8 0 0 0 0 16zm.93-9.412-1 4.705c-.07.34.029.533.304.533.194 0 .487-.07.686-.246l-.088.416c-.287.346-.92.598-1.465.598-.703 0-1.002-.422-.808-1.319l.738-3.468c.064-.293.006-.399-.287-.47l-.451-.081.082-.381 2.29-.287zM8 5.5a1 1 0 1 1 0-2 1 1 0 0 1 0 2z"/>
        </svg>
        {{ data.type }}
      </div>
      <div class="card-purple-body">
        <p class="card-text">{{ data.answer }}</p>
      </div>
    </div>
	
    <!-- 提醒创建结果卡片 -->
    <div v-else-if="intent === 'REMIND_ADD'" class="card-purple card-pink">
      <div class="card-purple-header">
        <svg xmlns="http://www.w3.org/2000/svg" width="14" height="14" fill="currentColor" viewBox="0 0 16 16">
          <path d="M8 3.5a.5.5 0 0 0-1 0V9a.5.5 0 0 0 .252.434l3.5 2a.5.5 0 0 0 .496-.868L8 8.71V3.5z"/>
          <path d="M8 16A8 8 0 1 0 8 0a8 8 0 0 0 0 16zm7-8A7 7 0 1 1 1 8a7 7 0 0 1 14 0z"/>
        </svg>
        智能提醒
      </div>
      <div class="card-purple-body">
        <p class="card-text" :class="data ? 'text-success' : 'text-danger'">
          {{ data ? '提醒创建成功，将在3小时后为你推送～' : '提醒创建失败，请重试！' }}
        </p>
        <router-link to="/remind" class="btn-view-remind">查看我的提醒</router-link>
      </div>
    </div>
  </div>
</template>

<script>
export default {
  name: 'ResultCard',
  props: {
    data: {
      type: [Object, Array],
      default: () => ({})
    },
    intent: {
      type: String,
      default: ''
    }
  }
}
</script>

<style scoped>
.result-card {
  margin-top: 12px;
}

/* 通用紫色卡片 */
.card-purple {
  border-radius: 12px;
  overflow: hidden;
  box-shadow: 0 4px 16px rgba(102, 126, 234, 0.2);
}

.card-purple-header {
  background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
  color: white;
  padding: 10px 14px;
  font-size: 13px;
  font-weight: 600;
  display: flex;
  align-items: center;
  gap: 6px;
}

.card-purple-body {
  background: white;
  padding: 12px 14px;
}

/* 生活服务卡片 - 绿色系 */
.card-green .card-purple-header {
  background: linear-gradient(135deg, #34d399 0%, #059669 100%);
}

/* 提醒卡片 - 粉色系 */
.card-pink .card-purple-header {
  background: linear-gradient(135deg, #f472b6 0%, #db2777 100%);
}

/* 课表表格 */
.schedule-table {
  width: 100%;
  border-collapse: collapse;
  font-size: 13px;
}

.schedule-table th {
  text-align: left;
  padding: 6px 8px;
  color: #6b7280;
  font-weight: 500;
  border-bottom: 1px solid #e5e7eb;
}

.schedule-table td {
  padding: 8px;
  color: #374151;
  border-bottom: 1px solid #f3f4f6;
}

.schedule-table tr:last-child td {
  border-bottom: none;
}

.schedule-table tr:hover td {
  background: #f9fafb;
}

.card-text {
  margin: 0 0 8px;
  color: #374151;
  font-size: 13px;
  line-height: 1.5;
}

.text-muted {
  color: #9ca3af;
}

.text-center {
  text-align: center;
}

/* 查看提醒按钮 */
.btn-view-remind {
  display: inline-block;
  padding: 6px 14px;
  background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
  color: white;
  border-radius: 8px;
  font-size: 12px;
  text-decoration: none;
  transition: all 0.25s ease;
}

.btn-view-remind:hover {
  opacity: 0.9;
  transform: translateY(-1px);
  box-shadow: 0 4px 12px rgba(102, 126, 234, 0.35);
}
</style>
