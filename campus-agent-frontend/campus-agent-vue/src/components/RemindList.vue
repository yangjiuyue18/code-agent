<template>
  <div class="card border-info">
    <div class="card-header bg-info text-white d-flex justify-content-between align-items-center">
      <h5 class="mb-0">我的提醒列表</h5>
      <span class="badge bg-light text-info">{{ remindList.length }}条提醒</span>
    </div>
    <div class="card-body">
      <table class="table table-sm table-hover">
        <thead>
          <tr>
            <th>提醒内容</th>
            <th>提醒时间</th>
            <th>状态</th>
          </tr>
        </thead>
        <tbody>
          <tr v-for="(item, index) in remindList" :key="index">
            <td>{{ item.content }}</td>
            <td>{{ formatTime(item.remindTime) }}</td>
            <td>
              <span class="badge" :class="item.status === 0 ? 'bg-warning' : 'bg-success'">
                {{ item.status === 0 ? '未完成' : '已完成' }}
              </span>
            </td>
          </tr>
          <tr v-if="remindList.length === 0">
            <td colspan="3" class="text-center text-muted">暂无提醒，快去首页创建吧～</td>
          </tr>
        </tbody>
      </table>
    </div>
  </div>
</template>

<script>
export default {
  name: 'RemindList',
  props: {
    remindList: {
      type: Array,
      default: () => []
    }
  },
  methods: {
    // 格式化时间
    formatTime(time) {
      if (!time) return ''
      const date = new Date(time)
      return date.toLocaleString('zh-CN', {
        year: 'numeric',
        month: '2-digit',
        day: '2-digit',
        hour: '2-digit',
        minute: '2-digit',
        second: '2-digit'
      })
    }
  }
}
</script>
