<template>
  <div class="mt-3">
    <!-- 课表卡片 -->
    <div v-if="intent === 'STUDY_SCHEDULE'" class="card border-primary">
      <div class="card-header bg-primary text-white">今日课表</div>
      <div class="card-body">
        <table class="table table-sm table-hover">
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
              <td colspan="3" class="text-center text-muted">今日无课，好好休息～</td>
            </tr>
          </tbody>
        </table>
      </div>
    </div>
	
    <!-- 生活服务/资讯卡片 -->
    <!--下面代码有问题：同一个标签上不能同时写 v-if 和 v-else-if，这是 Vue 绝对禁止的语法。
	<div v-else-if="intent === 'LIFE_SERVICE' || intent === 'CAMPUS_INFO'" class="card border-success" v-if="data">
      <div class="card-header bg-success text-white">{{ data.type }}</div>
      <div class="card-body">
        <p class="card-text">{{ data.answer }}</p>
      </div>
    </div>
	更改后的代码如下：
	-->
	<!-- 生活服务/资讯卡片 -->
    <div v-if="data && (intent === 'LIFE_SERVICE' || intent === 'CAMPUS_INFO')" class="card border-success">
      <div class="card-header bg-success text-white">{{ data.type }}</div>
      <div class="card-body">
        <p class="card-text">{{ data.answer }}</p>
      </div>
    </div>
	
    <!-- 提醒创建结果卡片 -->
    <div v-else-if="intent === 'REMIND_ADD'" class="card border-info">
      <div class="card-header bg-info text-white">智能提醒</div>
      <div class="card-body">
        <p class="card-text" :class="data ? 'text-success' : 'text-danger'">
          {{ data ? '提醒创建成功，将在3小时后为你推送～' : '提醒创建失败，请重试！' }}
        </p>
        <router-link to="/remind" class="btn btn-sm btn-info text-white">查看我的提醒</router-link>
      </div>
    </div>
  </div>
</template>

<script>
export default {
  name: 'ResultCard',
  props: {
    data: { // 结果数据
      type: [Object, Array],
      default: () => ({})
    },
    intent: { // 意图类型
      type: String,
      default: ''
    }
  }
}
</script>

<style scoped>
.card {
  box-shadow: 0 2px 4px rgba(0,0,0,0.1);
}
</style>
