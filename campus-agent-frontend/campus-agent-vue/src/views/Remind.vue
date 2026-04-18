<template>
  <div class="page-container">
    <!-- 背景装饰 -->
    <div class="bg-decoration">
      <div class="circle circle-1"></div>
      <div class="circle circle-2"></div>
    </div>
    
    <div class="main-content">
      <div class="row justify-content-center">
        <div class="col-lg-8 col-xl-7">
          <!-- 返回按钮 -->
          <button class="back-btn" @click="$router.push('/')">
            <svg xmlns="http://www.w3.org/2000/svg" width="18" height="18" fill="currentColor" viewBox="0 0 16 16">
              <path fill-rule="evenodd" d="M12.5 15a.5.5 0 0 1-.5-.5v-13a.5.5 0 0 1 1 0v13a.5.5 0 0 1-.5.5zM11 2.5V5H4a1 1 0 0 0-1 1v7.586l-2-2a1 1 0 0 0-1.414 1.414l3 3a1 1 0 0 0 1.414 0l3-3A1 1 0 0 0 10 10.586V7a1 1 0 0 0-1-1h7z"/>
            </svg>
            返回主页
          </button>
          
          <!-- 页面标题 -->
          <div class="page-header text-center mb-4">
            <div class="header-icon">
              <svg xmlns="http://www.w3.org/2000/svg" width="28" height="28" fill="currentColor" viewBox="0 0 16 16">
                <path d="M8 3.5a.5.5 0 0 0-1 0V9a.5.5 0 0 0 .252.434l3.5 2a.5.5 0 0 0 .496-.868L8 8.71V3.5z"/>
                <path d="M8 16A8 8 0 1 0 8 0a8 8 0 0 0 0 16zm7-8A7 7 0 1 1 1 8a7 7 0 0 1 14 0z"/>
              </svg>
            </div>
            <h2 class="header-title">我的提醒</h2>
            <p class="header-subtitle">记录你的待办，不遗漏重要事项</p>
          </div>
          
          <!-- 提醒列表卡片 -->
          <div class="content-card">
            <div class="card-header-custom">
              <div class="d-flex align-items-center">
                <svg xmlns="http://www.w3.org/2000/svg" width="20" height="20" fill="currentColor" viewBox="0 0 16 16" class="me-2">
                  <path d="M11.742 10.344a6.5 6.5 0 1 0-1.397 1.398h-.001c.03.04.062.078.098.115l3.85 3.85a1 1 0 0 0 1.415-1.414l-3.85-3.85a1.007 1.007 0 0 0-.115-.1zM12 6.5a5.5 5.5 0 1 1-11 0 5.5 5.5 0 0 1 11 0z"/>
                </svg>
                提醒列表
              </div>
              <span class="count-badge">{{ remindList.length }} 条</span>
            </div>
            <div class="card-body-custom">
              <!-- 提醒列表 -->
              <div v-if="remindList.length > 0" class="remind-list">
                <div v-for="(item, index) in remindList" :key="index" class="remind-item" :class="item.status === 1 ? 'completed' : ''">
                  <div class="remind-icon" :class="item.status === 0 ? 'pending' : 'done'">
                    <svg v-if="item.status === 0" xmlns="http://www.w3.org/2000/svg" width="16" height="16" fill="currentColor" viewBox="0 0 16 16">
                      <path d="M8 3.5a.5.5 0 0 0-1 0V9a.5.5 0 0 0 .252.434l3.5 2a.5.5 0 0 0 .496-.868L8 8.71V3.5z"/>
                      <path d="M8 16A8 8 0 1 0 8 0a8 8 0 0 0 0 16zm7-8A7 7 0 1 1 1 8a7 7 0 0 1 14 0z"/>
                    </svg>
                    <svg v-else xmlns="http://www.w3.org/2000/svg" width="16" height="16" fill="currentColor" viewBox="0 0 16 16">
                      <path d="M13.854 3.646a.5.5 0 0 1 0 .708l-7 7a.5.5 0 0 1-.708 0l-3.5-3.5a.5.5 0 1 1 .708-.708L6.5 10.293l6.646-6.647a.5.5 0 0 1 .708 0z"/>
                    </svg>
                  </div>
                  <div class="remind-content">
                    <div class="remind-text">{{ item.content }}</div>
                    <div class="remind-time">
                      <svg xmlns="http://www.w3.org/2000/svg" width="12" height="12" fill="currentColor" viewBox="0 0 16 16" class="me-1">
                        <path d="M8 3.5a.5.5 0 0 0-1 0V9a.5.5 0 0 0 .252.434l3.5 2a.5.5 0 0 0 .496-.868L8 8.71V3.5z"/>
                        <path d="M8 16A8 8 0 1 0 8 0a8 8 0 0 0 0 16zm7-8A7 7 0 1 1 1 8a7 7 0 0 1 14 0z"/>
                      </svg>
                      {{ formatTime(item.remindTime) }}
                    </div>
                  </div>
                  <div class="remind-status">
                    <span class="status-badge" :class="item.status === 0 ? 'pending' : 'done'">
                      {{ item.status === 0 ? '待完成' : '已完成' }}
                    </span>
                  </div>
                </div>
              </div>
              
              <!-- 空状态 -->
              <div v-else class="empty-state">
                <div class="empty-icon">
                  <svg xmlns="http://www.w3.org/2000/svg" width="48" height="48" fill="currentColor" viewBox="0 0 16 16">
                    <path d="M8 3.5a.5.5 0 0 0-1 0V9a.5.5 0 0 0 .252.434l3.5 2a.5.5 0 0 0 .496-.868L8 8.71V3.5z"/>
                    <path d="M8 16A8 8 0 1 0 8 0a8 8 0 0 0 0 16zm7-8A7 7 0 1 1 1 8a7 7 0 0 1 14 0z"/>
                  </svg>
                </div>
                <div class="empty-text">暂无提醒</div>
                <div class="empty-hint">快去首页创建提醒吧～</div>
                <button class="btn-create" @click="$router.push('/')">
                  <svg xmlns="http://www.w3.org/2000/svg" width="16" height="16" fill="currentColor" viewBox="0 0 16 16" class="me-2">
                    <path d="M8 4a.5.5 0 0 1 .5.5v3h3a.5.5 0 0 1 0 1h-3v3a.5.5 0 0 1-1 0v-3h-3a.5.5 0 0 1 0-1h3v-3A.5.5 0 0 1 8 4z"/>
                  </svg>
                  去创建提醒
                </button>
              </div>
            </div>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
export default {
  name: 'Remind',
  data() {
    return {
      remindList: [],
      userId: this.$cookies.get('userId') || 1
    }
  },
  mounted() {
    this.getRemindList()
  },
  methods: {
    async getRemindList() {
      try {
        const res = await this.$axios.get('/remind/list', {
          params: { userId: this.userId }
        })
        if (res.data.code === 200) {
          this.remindList = res.data.data
        }
      } catch (err) {
        console.error(err)
      }
    },
    formatTime(time) {
      if (!time) return ''
      const date = new Date(time)
      return date.toLocaleString('zh-CN', {
        month: '2-digit',
        day: '2-digit',
        hour: '2-digit',
        minute: '2-digit'
      })
    }
  }
}
</script>

<style scoped>
.page-container {
  min-height: 100vh;
  position: relative;
  overflow: hidden;
  background: linear-gradient(135deg, #f5f7fa 0%, #e4e8f0 50%, #f0f2f5 100%);
}

.bg-decoration {
  position: fixed;
  top: 0;
  left: 0;
  width: 100%;
  height: 100%;
  pointer-events: none;
  z-index: 0;
}

.circle {
  position: absolute;
  border-radius: 50%;
  filter: blur(80px);
  opacity: 0.5;
  animation: float 20s ease-in-out infinite;
}

.circle-1 {
  width: 350px;
  height: 350px;
  background: linear-gradient(135deg, #f093fb 0%, #f5576c 100%);
  top: -80px;
  left: -80px;
}

.circle-2 {
  width: 300px;
  height: 300px;
  background: linear-gradient(135deg, #4facfe 0%, #00f2fe 100%);
  bottom: -60px;
  right: -60px;
  animation-delay: -5s;
}

@keyframes float {
  0%, 100% { transform: translate(0, 0) scale(1); }
  50% { transform: translate(20px, -20px) scale(1.05); }
}

.main-content {
  position: relative;
  z-index: 1;
  padding: 30px 20px 50px;
}

/* 页面标题 */
.page-header {
  animation: slideUp 0.6s ease-out;
}

.header-icon {
  width: 64px;
  height: 64px;
  background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
  border-radius: 18px;
  display: flex;
  align-items: center;
  justify-content: center;
  color: white;
  margin: 0 auto 16px;
  box-shadow: 0 8px 24px rgba(102, 126, 234, 0.3);
}

.header-title {
  font-size: 1.75rem;
  font-weight: 700;
  color: #1a1a2e;
  margin-bottom: 8px;
}

.header-subtitle {
  color: #6b7280;
  font-size: 15px;
}

/* 内容卡片 */
.content-card {
  background: white;
  border-radius: 20px;
  box-shadow: 0 10px 40px rgba(0, 0, 0, 0.08);
  overflow: hidden;
  animation: slideUp 0.6s ease-out 0.1s both;
}

@keyframes slideUp {
  from { opacity: 0; transform: translateY(20px); }
  to { opacity: 1; transform: translateY(0); }
}

.card-header-custom {
  display: flex;
  align-items: center;
  justify-content: space-between;
  padding: 18px 24px;
  background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
  color: white;
  font-weight: 600;
  font-size: 16px;
}

.count-badge {
  background: rgba(255, 255, 255, 0.2);
  padding: 4px 12px;
  border-radius: 20px;
  font-size: 13px;
  font-weight: 500;
}

.card-body-custom {
  padding: 20px;
}

/* 提醒列表 */
.remind-list {
  display: flex;
  flex-direction: column;
  gap: 12px;
}

.remind-item {
  display: flex;
  align-items: center;
  gap: 16px;
  padding: 16px 20px;
  background: #f8f9fa;
  border-radius: 12px;
  transition: all 0.3s ease;
  border: 1px solid transparent;
}

.remind-item:hover {
  transform: translateX(4px);
  border-color: #667eea;
  box-shadow: 0 4px 12px rgba(102, 126, 234, 0.1);
}

.remind-item.completed {
  opacity: 0.6;
}

.remind-icon {
  width: 40px;
  height: 40px;
  border-radius: 12px;
  display: flex;
  align-items: center;
  justify-content: center;
  flex-shrink: 0;
}

.remind-icon.pending {
  background: linear-gradient(135deg, #ff9a56 0%, #ff6a00 100%);
  color: white;
}

.remind-icon.done {
  background: linear-gradient(135deg, #56d364 0%, #2bb673 100%);
  color: white;
}

.remind-content {
  flex: 1;
  min-width: 0;
}

.remind-text {
  font-size: 15px;
  font-weight: 500;
  color: #1a1a2e;
  margin-bottom: 4px;
}

.remind-time {
  display: flex;
  align-items: center;
  font-size: 13px;
  color: #6b7280;
}

.remind-status {
  flex-shrink: 0;
}

.status-badge {
  padding: 6px 14px;
  border-radius: 20px;
  font-size: 12px;
  font-weight: 600;
}

.status-badge.pending {
  background: rgba(255, 154, 86, 0.15);
  color: #ff6a00;
}

.status-badge.done {
  background: rgba(86, 211, 100, 0.15);
  color: #2bb673;
}

/* 空状态 */
.empty-state {
  text-align: center;
  padding: 48px 20px;
}

.empty-icon {
  width: 80px;
  height: 80px;
  background: linear-gradient(135deg, #f8f9fa 0%, #e9ecef 100%);
  border-radius: 50%;
  display: flex;
  align-items: center;
  justify-content: center;
  color: #adb5bd;
  margin: 0 auto 20px;
}

.empty-text {
  font-size: 18px;
  font-weight: 600;
  color: #495057;
  margin-bottom: 8px;
}

.empty-hint {
  color: #6b7280;
  font-size: 14px;
  margin-bottom: 24px;
}

.btn-create {
  display: inline-flex;
  align-items: center;
  padding: 12px 24px;
  background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
  color: white;
  border: none;
  border-radius: 12px;
  font-size: 14px;
  font-weight: 600;
  cursor: pointer;
  transition: all 0.3s ease;
  box-shadow: 0 4px 12px rgba(102, 126, 234, 0.3);
}

.btn-create:hover {
  transform: translateY(-2px);
  box-shadow: 0 6px 20px rgba(102, 126, 234, 0.4);
}

/* 返回按钮 */
.back-btn {
  display: inline-flex;
  align-items: center;
  gap: 6px;
  padding: 8px 16px;
  background: white;
  border: 1px solid #e9ecef;
  border-radius: 20px;
  font-size: 13px;
  color: #6b7280;
  cursor: pointer;
  transition: all 0.3s ease;
  margin-bottom: 16px;
  box-shadow: 0 2px 8px rgba(0, 0, 0, 0.04);
}

.back-btn:hover {
  background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
  color: white;
  border-color: transparent;
  transform: translateX(-4px);
  box-shadow: 0 4px 12px rgba(102, 126, 234, 0.3);
}
</style>
