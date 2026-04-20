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
            <svg xmlns="http://www.w3.org/2000/svg" width="14" height="14" fill="currentColor" viewBox="0 0 16 16">
              <path fill-rule="evenodd" d="M11.354 1.646a.5.5 0 0 1 0 .708L5.707 8l5.647 5.646a.5.5 0 0 1-.708.708l-6-6a.5.5 0 0 1 0-.708l6-6a.5.5 0 0 1 .708 0z"/>
            </svg>
            返回
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
              <span class="count-badge">{{ filteredList.length }} 条</span>
            </div>
            <div class="card-body-custom">
              <!-- 卡片头部区域 -->
              <div class="list-header">
                <div class="search-wrapper">
                  <svg xmlns="http://www.w3.org/2000/svg" width="15" height="15" fill="currentColor" viewBox="0 0 16 16" class="search-icon">
                    <path d="M11.742 10.344a6.5 6.5 0 1 0-1.397 1.398h-.001c.03.04.062.078.098.115l3.85 3.85a1 1 0 0 0 1.415-1.414l-3.85-3.85a1.007 1.007 0 0 0-.115-.1zM12 6.5a5.5 5.5 0 1 1-11 0 5.5 5.5 0 0 1 11 0z"/>
                  </svg>
                  <input 
                    v-model="searchKeyword" 
                    type="text" 
                    placeholder="搜索提醒..." 
                    class="search-input"
                  >
                </div>
                <button class="btn-add" @click="showAddModal = true">
                  <svg xmlns="http://www.w3.org/2000/svg" width="14" height="14" fill="currentColor" viewBox="0 0 16 16">
                    <path d="M8 4a.5.5 0 0 1 .5.5v3h3a.5.5 0 0 1 0 1h-3v3a.5.5 0 0 1-1 0v-3h-3a.5.5 0 0 1 0-1h3v-3A.5.5 0 0 1 8 4z"/>
                  </svg>
                  添加
                </button>
              </div>
              
              <!-- 提醒列表 -->
              <div v-if="filteredList.length > 0" class="remind-list">
                <div v-for="(item, index) in filteredList" :key="item.id" class="remind-item" :class="item.status === 1 ? 'completed' : ''">
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
                    <span v-if="item.status === 0" class="status-badge pending" @click="toggleStatus(item)">
                      待完成
                    </span>
                    <span v-else class="status-badge done" @click="toggleStatus(item)">
                      已完成
                    </span>
                    <button v-if="item.status === 1" class="btn-clear" @click="clearRemind(item)" title="清除">
                      <svg xmlns="http://www.w3.org/2000/svg" width="14" height="14" fill="currentColor" viewBox="0 0 16 16">
                        <path d="M5.5 5.5A.5.5 0 0 1 6 6v6a.5.5 0 0 1-1 0V6a.5.5 0 0 1 .5-.5zm2.5 0a.5.5 0 0 1 .5.5v6a.5.5 0 0 1-1 0V6a.5.5 0 0 1 .5-.5zm3 .5a.5.5 0 0 0-1 0v6a.5.5 0 0 0 1 0V6z"/>
                        <path fill-rule="evenodd" d="M14.5 3a1 1 0 0 1-1 1H13v9a2 2 0 0 1-2 2H5a2 2 0 0 1-2-2V4h-.5a1 1 0 0 1-1-1V2a1 1 0 0 1 1-1H6a1 1 0 0 1 1-1h2a1 1 0 0 1 1 1h3.5a1 1 0 0 1 1 1v1zM4.118 4 4 4.059V13a1 1 0 0 0 1 1h6a1 1 0 0 0 1-1V4.059L11.882 4H4.118zM2.5 3V2h11v1h-11z"/>
                      </svg>
                    </button>
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
    
    <!-- 添加提醒弹窗 -->
    <div v-if="showAddModal" class="modal-overlay" @click.self="showAddModal = false">
      <div class="modal-content">
        <div class="modal-header">
          <h3>添加提醒</h3>
          <button class="modal-close" @click="showAddModal = false">×</button>
        </div>
        <div class="modal-body">
          <div class="form-group">
            <label>提醒内容</label>
            <input v-model="newRemind.content" type="text" placeholder="请输入提醒内容" class="form-input">
          </div>
          <div class="form-group">
            <label>提醒时间</label>
            <input v-model="newRemind.remindTime" type="datetime-local" class="form-input">
          </div>
        </div>
        <div class="modal-footer">
          <button class="btn-cancel" @click="showAddModal = false">取消</button>
          <button class="btn-confirm" @click="addRemind">确认添加</button>
        </div>
      </div>
    </div>
    
    <!-- 清除确认弹窗 -->
    <div v-if="showClearModal" class="modal-overlay" @click.self="showClearModal = false">
      <div class="modal-content">
        <div class="modal-header">
          <h3>确认清除</h3>
          <button class="modal-close" @click="showClearModal = false">×</button>
        </div>
        <div class="modal-body">
          <p class="confirm-text">确定要清除这条提醒吗？</p>
        </div>
        <div class="modal-footer">
          <button class="btn-cancel" @click="showClearModal = false">取消</button>
          <button class="btn-danger" @click="confirmClear">确认清除</button>
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
      searchKeyword: '',
      userId: this.$cookies.get('userId') || 1,
      showAddModal: false,
      showClearModal: false,
      clearTarget: null,
      newRemind: {
        content: '',
        remindTime: ''
      }
    }
  },
  computed: {
    filteredList() {
      if (!this.searchKeyword) {
        return this.remindList
      }
      const keyword = this.searchKeyword.toLowerCase()
      return this.remindList.filter(item => 
        item.content.toLowerCase().includes(keyword)
      )
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
    },
    async addRemind() {
      if (!this.newRemind.content || !this.newRemind.remindTime) {
        alert('请填写完整信息')
        return
      }
      try {
        const res = await this.$axios.post('/remind/add', {
          userId: this.userId,
          content: this.newRemind.content,
          remindTime: this.newRemind.remindTime
        })
        if (res.data.code === 200) {
          this.showAddModal = false
          this.newRemind = { content: '', remindTime: '' }
          this.getRemindList()
        }
      } catch (err) {
        console.error(err)
      }
    },
    async toggleStatus(item) {
      try {
        const newStatus = item.status === 0 ? 1 : 0
        const res = await this.$axios.put('/remind/update', {
          id: item.id,
          status: newStatus
        })
        if (res.data.code === 200) {
          item.status = newStatus
        }
      } catch (err) {
        console.error(err)
      }
    },
    clearRemind(item) {
      this.clearTarget = item
      this.showClearModal = true
    },
    async confirmClear() {
      if (!this.clearTarget) return
      try {
        const res = await this.$axios.delete('/remind/delete', {
          params: { id: this.clearTarget.id }
        })
        if (res.data.code === 200) {
          const idx = this.remindList.findIndex(i => i.id === this.clearTarget.id)
          if (idx > -1) {
            this.remindList.splice(idx, 1)
          }
        }
      } catch (err) {
        console.error(err)
      }
      this.showClearModal = false
      this.clearTarget = null
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

/* 列表头部 */
.list-header {
  display: flex;
  align-items: center;
  justify-content: space-between;
  margin-bottom: 16px;
  padding-bottom: 12px;
  border-bottom: 1px solid #eee;
  gap: 12px;
}

.search-wrapper {
  flex: 1;
  display: flex;
  align-items: center;
  background: #f5f5f5;
  border-radius: 20px;
  padding: 8px 14px;
  gap: 8px;
  transition: all 0.25s ease;
}

.search-wrapper:focus-within {
  background: #f0f0f0;
  box-shadow: 0 0 0 2px rgba(102, 126, 234, 0.15);
}

.search-wrapper .search-icon {
  color: #999;
  flex-shrink: 0;
}

.search-wrapper .search-input {
  flex: 1;
  background: transparent;
  border: none;
  outline: none;
  color: #333;
  font-size: 13px;
}

.search-wrapper .search-input::placeholder {
  color: #aaa;
}

.btn-add {
  display: inline-flex;
  align-items: center;
  gap: 5px;
  padding: 8px 16px;
  background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
  color: white;
  border: none;
  border-radius: 20px;
  font-size: 13px;
  font-weight: 500;
  cursor: pointer;
  transition: all 0.3s ease;
  box-shadow: 0 2px 8px rgba(102, 126, 234, 0.25);
  flex-shrink: 0;
}

.btn-add:hover {
  transform: translateY(-1px);
  box-shadow: 0 4px 12px rgba(102, 126, 234, 0.35);
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
  gap: 5px;
  padding: 7px 14px;
  background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
  border: none;
  border-radius: 20px;
  font-size: 13px;
  font-weight: 500;
  color: white;
  cursor: pointer;
  transition: all 0.25s ease;
  margin-bottom: 16px;
  box-shadow: 0 3px 12px rgba(102, 126, 234, 0.3);
  letter-spacing: 0.3px;
}

.back-btn:hover {
  transform: translateX(-3px) scale(1.03);
  box-shadow: 0 6px 18px rgba(102, 126, 234, 0.4);
}

.back-btn:active {
  transform: translateX(-1px) scale(0.98);
}

/* 清除按钮 */
.btn-clear {
  display: inline-flex;
  align-items: center;
  justify-content: center;
  width: 26px;
  height: 26px;
  margin-left: 6px;
  background: rgba(220, 53, 69, 0.12);
  color: #dc3545;
  border: none;
  border-radius: 6px;
  cursor: pointer;
  transition: all 0.25s ease;
  flex-shrink: 0;
}

.btn-clear:hover {
  background: #dc3545;
  color: white;
  transform: scale(1.1);
}

/* 状态徽章可点击 */
.status-badge {
  cursor: pointer;
  transition: all 0.25s ease;
}

.status-badge:hover {
  transform: scale(1.05);
}

/* 弹窗样式 */
.modal-overlay {
  position: fixed;
  top: 0;
  left: 0;
  width: 100%;
  height: 100%;
  background: rgba(0, 0, 0, 0.5);
  display: flex;
  align-items: center;
  justify-content: center;
  z-index: 1000;
  animation: fadeIn 0.2s ease;
}

@keyframes fadeIn {
  from { opacity: 0; }
  to { opacity: 1; }
}

.modal-content {
  background: white;
  border-radius: 16px;
  width: 90%;
  max-width: 400px;
  box-shadow: 0 20px 60px rgba(0, 0, 0, 0.2);
  animation: slideUp 0.3s ease;
}

@keyframes slideUp {
  from { opacity: 0; transform: translateY(20px); }
  to { opacity: 1; transform: translateY(0); }
}

.modal-header {
  display: flex;
  align-items: center;
  justify-content: space-between;
  padding: 20px 24px;
  border-bottom: 1px solid #eee;
}

.modal-header h3 {
  margin: 0;
  font-size: 18px;
  font-weight: 600;
  color: #1a1a2e;
}

.modal-close {
  width: 32px;
  height: 32px;
  background: #f5f5f5;
  border: none;
  border-radius: 50%;
  font-size: 20px;
  color: #666;
  cursor: pointer;
  transition: all 0.25s ease;
}

.modal-close:hover {
  background: #e0e0e0;
  color: #333;
}

.modal-body {
  padding: 24px;
}

.form-group {
  margin-bottom: 20px;
}

.form-group:last-child {
  margin-bottom: 0;
}

.form-group label {
  display: block;
  margin-bottom: 8px;
  font-size: 14px;
  font-weight: 500;
  color: #333;
}

.form-input {
  width: 100%;
  padding: 12px 16px;
  border: 1px solid #e0e0e0;
  border-radius: 10px;
  font-size: 14px;
  transition: all 0.25s ease;
  box-sizing: border-box;
}

.form-input:focus {
  outline: none;
  border-color: #667eea;
  box-shadow: 0 0 0 3px rgba(102, 126, 234, 0.15);
}

.confirm-text {
  text-align: center;
  font-size: 15px;
  color: #333;
  margin: 0;
}

.modal-footer {
  display: flex;
  gap: 12px;
  padding: 16px 24px 24px;
}

.btn-cancel,
.btn-confirm,
.btn-danger {
  flex: 1;
  padding: 12px;
  border: none;
  border-radius: 10px;
  font-size: 14px;
  font-weight: 600;
  cursor: pointer;
  transition: all 0.25s ease;
}

.btn-cancel {
  background: #f5f5f5;
  color: #666;
}

.btn-cancel:hover {
  background: #e0e0e0;
  color: #333;
}

.btn-confirm {
  background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
  color: white;
}

.btn-confirm:hover {
  transform: translateY(-2px);
  box-shadow: 0 4px 12px rgba(102, 126, 234, 0.35);
}

.btn-danger {
  background: linear-gradient(135deg, #f5576c 0%, #e63946 100%);
  color: white;
}

.btn-danger:hover {
  transform: translateY(-2px);
  box-shadow: 0 4px 12px rgba(220, 53, 69, 0.35);
}
</style>
