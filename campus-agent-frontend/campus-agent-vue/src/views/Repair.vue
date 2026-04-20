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
                <path d="M14.763.075A.5.5 0 0 1 15 .5v15a.5.5 0 0 1-.5.5h-3a.5.5 0 0 1-.5-.5V14h-1v1.5a.5.5 0 0 1-.5.5h-9a.5.5 0 0 1-.5-.5V10a.5.5 0 0 1 .342-.474L6 7.64V4.5a.5.5 0 0 1 .276-.447l8-4a.5.5 0 0 1 .487.022zM6 8.694 1 10.36V15h5V8.694zM15 1h-4.5a.5.5 0 0 0-.5.5V11h1V1.5a.5.5 0 0 0-.5-.5z"/>
              </svg>
            </div>
            <h2 class="header-title">报修服务</h2>
            <p class="header-subtitle">一键拨打，快速响应</p>
          </div>

          <!-- 报修电话列表 -->
          <div class="phone-list">
            <div v-for="(item, index) in phoneList" :key="index" class="phone-card" @click="callPhone(item.phone)">
              <div class="phone-icon" :style="{ background: getIconBg(index) }">
                {{ item.icon }}
              </div>
              <div class="phone-info">
                <h3>{{ item.title }}</h3>
                <p>{{ item.desc }}</p>
              </div>
              <div class="phone-number">
                <span class="number">{{ item.phone }}</span>
                <span class="call-btn">
                  <svg xmlns="http://www.w3.org/2000/svg" width="20" height="20" fill="currentColor" viewBox="0 0 16 16">
                    <path d="M3.654 1.328a.678.678 0 0 0-1.015-.063L1.605 2.3c-.483.484-.661 1.169-.45 1.77a17.568 17.568 0 0 0 4.168 6.608 17.569 17.569 0 0 0 6.608 4.168c.601.211 1.286.033 1.77-.45l1.034-1.034a.678.678 0 0 0-.063-1.015l-2.307-1.794a.678.678 0 0 0-.58-.122l-2.19.547a1.745 1.745 0 0 1-1.657-.459L5.482 8.062a1.745 1.745 0 0 1-.46-1.657l.548-2.19a.678.678 0 0 0-.122-.58L3.654 1.328zM1.884.511a1.745 1.745 0 0 1 2.612.163L6.29 2.98c.329.423.445.974.315 1.494l-.547 2.19a.678.678 0 0 0 .178.643l2.457 2.457a.678.678 0 0 0 .644.178l2.189-.547a1.745 1.745 0 0 1 1.494.315l2.306 1.794c.829.645.905 1.87.163 2.611l-1.034 1.034c-.74.74-1.846 1.065-2.877.702a18.634 18.634 0 0 1-7.01-4.42 18.634 18.634 0 0 1-4.42-7.009c-.362-1.03-.037-2.137.703-2.877L1.885.511z"/>
                  </svg>
                </span>
              </div>
            </div>
          </div>

          <!-- 在线报修 -->
          <div class="online-repair">
            <div class="repair-header">
              <svg xmlns="http://www.w3.org/2000/svg" width="20" height="20" fill="currentColor" viewBox="0 0 16 16">
                <path d="M11 6.5a.5.5 0 0 1 .5-.5h1a.5.5 0 0 1 .5.5v1a.5.5 0 0 1-.5.5h-1a.5.5 0 0 1-.5-.5v-1zm-3 0a.5.5 0 0 1 .5-.5h1a.5.5 0 0 1 .5.5v1a.5.5 0 0 1-.5.5h-1a.5.5 0 0 1-.5-.5v-1zm-5 3a.5.5 0 0 1 .5-.5h1a.5.5 0 0 1 .5.5v1a.5.5 0 0 1-.5.5h-1a.5.5 0 0 1-.5-.5v-1zm3 0a.5.5 0 0 1 .5-.5h1a.5.5 0 0 1 .5.5v1a.5.5 0 0 1-.5.5h-1a.5.5 0 0 1-.5-.5v-1z"/>
                <path d="M3.5 0a.5.5 0 0 1 .5.5V1h8V.5a.5.5 0 0 1 1 0V1h1a2 2 0 0 1 2 2v11a2 2 0 0 1-2 2H2a2 2 0 0 1-2-2V3a2 2 0 0 1 2-2h1V.5a.5.5 0 0 1 .5-.5zM1 4v10a1 1 0 0 0 1 1h12a1 1 0 0 0 1-1V4H1z"/>
              </svg>
              <span>在线提交报修</span>
            </div>
            <div class="repair-form">
              <div class="form-group">
                <label>报修类型</label>
                <select v-model="repairForm.type">
                  <option value="">请选择报修类型</option>
                  <option value="water">💧 水电维修</option>
                  <option value="furniture">🛏️ 家具维修</option>
                  <option value="network">📶 网络故障</option>
                  <option value="other">📋 其他</option>
                </select>
              </div>
              <div class="form-group">
                <label>报修地点</label>
                <input v-model="repairForm.location" type="text" placeholder="如：学生宿舍3号楼201" />
              </div>
              <div class="form-group">
                <label>问题描述</label>
                <textarea v-model="repairForm.description" placeholder="请详细描述您遇到的问题..." rows="3"></textarea>
              </div>
              <button class="submit-btn" @click="submitRepair">
                <svg xmlns="http://www.w3.org/2000/svg" width="18" height="18" fill="currentColor" viewBox="0 0 16 16">
                  <path d="M1 14.5a.5.5 0 0 1 .5-.5h13a.5.5 0 0 1 0 1h-13a.5.5 0 0 1-.5-.5zM1 2.5a.5.5 0 0 1 .5-.5h13a.5.5 0 0 1 0 1h-13a.5.5 0 0 1-.5-.5zm0 12a.5.5 0 0 1 .5-.5h13a.5.5 0 0 1 0 1h-13a.5.5 0 0 1-.5-.5z"/>
                  <path d="M2 10a.5.5 0 0 1 .5-.5h11a.5.5 0 0 1 0 1h-11a.5.5 0 0 1-.5-.5zm0-4a.5.5 0 0 1 .5-.5h11a.5.5 0 0 1 0 1h-11a.5.5 0 0 1-.5-.5zm0-4a.5.5 0 0 1 .5-.5h11a.5.5 0 0 1 0 1h-11a.5.5 0 0 1-.5-.5z"/>
                </svg>
                提交报修
              </button>
            </div>
          </div>
        </div>
      </div>
    </div>

    <!-- 提示弹窗 -->
    <div v-if="showToast" class="toast">{{ toastMessage }}</div>
  </div>
</template>

<script>
export default {
  name: 'Repair',
  data() {
    return {
      phoneList: [
        {
          title: '后勤报修热线',
          icon: '🏠',
          desc: '宿舍水电、家具等维修',
          phone: '0574-88888888'
        },
        {
          title: '网络信息中心',
          icon: '📶',
          desc: '校园网、WiFi故障',
          phone: '0574-88888889'
        },
        {
          title: '物业服务中心',
          icon: '🔧',
          desc: '公共区域设施维修',
          phone: '0574-88888880'
        },
        {
          title: '校园安全热线',
          icon: '🚨',
          desc: '紧急情况、安全问题',
          phone: '0574-88888810'
        }
      ],
      repairForm: {
        type: '',
        location: '',
        description: ''
      },
      showToast: false,
      toastMessage: ''
    }
  },
  methods: {
    getIconBg(index) {
      const colors = [
        'linear-gradient(135deg, #4facfe 0%, #00f2fe 100%)',
        'linear-gradient(135deg, #667eea 0%, #764ba2 100%)',
        'linear-gradient(135deg, #f093fb 0%, #f5576c 100%)',
        'linear-gradient(135deg, #ff6b6b 0%, #ee5a24 100%)'
      ]
      return colors[index % colors.length]
    },
    callPhone(phone) {
      window.location.href = `tel:${phone}`
      this.showToastMessage(`正在拨打 ${phone}...`)
    },
    showToastMessage(msg) {
      this.toastMessage = msg
      this.showToast = true
      setTimeout(() => {
        this.showToast = false
      }, 2000)
    },
    submitRepair() {
      if (!this.repairForm.type || !this.repairForm.location || !this.repairForm.description) {
        this.showToastMessage('请填写完整的报修信息')
        return
      }
      // TODO: 调用后端API提交报修
      this.showToastMessage('报修提交成功！我们会尽快处理')
      this.repairForm = { type: '', location: '', description: '' }
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
  background: linear-gradient(135deg, #4facfe 0%, #00f2fe 100%);
  top: -80px;
  right: -80px;
}

.circle-2 {
  width: 300px;
  height: 300px;
  background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
  bottom: -60px;
  left: -60px;
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

.page-header {
  animation: slideUp 0.6s ease-out;
}

.header-icon {
  width: 64px;
  height: 64px;
  background: linear-gradient(135deg, #4facfe 0%, #00f2fe 100%);
  border-radius: 18px;
  display: flex;
  align-items: center;
  justify-content: center;
  color: white;
  margin: 0 auto 16px;
  box-shadow: 0 8px 24px rgba(79, 172, 254, 0.3);
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

@keyframes slideUp {
  from { opacity: 0; transform: translateY(20px); }
  to { opacity: 1; transform: translateY(0); }
}

/* 电话卡片列表 */
.phone-list {
  display: flex;
  flex-direction: column;
  gap: 12px;
  animation: slideUp 0.6s ease-out 0.1s both;
}

.phone-card {
  background: white;
  border-radius: 16px;
  padding: 20px;
  display: flex;
  align-items: center;
  gap: 16px;
  box-shadow: 0 4px 20px rgba(0, 0, 0, 0.06);
  cursor: pointer;
  transition: all 0.3s ease;
}

.phone-card:hover {
  transform: translateY(-4px);
  box-shadow: 0 8px 30px rgba(0, 0, 0, 0.1);
}

.phone-icon {
  width: 52px;
  height: 52px;
  border-radius: 14px;
  display: flex;
  align-items: center;
  justify-content: center;
  font-size: 24px;
  box-shadow: 0 4px 12px rgba(0, 0, 0, 0.1);
}

.phone-info {
  flex: 1;
}

.phone-info h3 {
  font-size: 16px;
  font-weight: 600;
  color: #1a1a2e;
  margin-bottom: 4px;
}

.phone-info p {
  font-size: 13px;
  color: #6b7280;
  margin: 0;
}

.phone-number {
  display: flex;
  align-items: center;
  gap: 12px;
}

.number {
  font-size: 16px;
  font-weight: 600;
  color: #1a1a2e;
}

.call-btn {
  width: 44px;
  height: 44px;
  background: linear-gradient(135deg, #4facfe 0%, #00f2fe 100%);
  border-radius: 50%;
  display: flex;
  align-items: center;
  justify-content: center;
  color: white;
  box-shadow: 0 4px 12px rgba(79, 172, 254, 0.3);
  transition: all 0.3s ease;
}

.phone-card:hover .call-btn {
  transform: scale(1.1);
}

/* 在线报修 */
.online-repair {
  margin-top: 24px;
  background: white;
  border-radius: 16px;
  padding: 24px;
  box-shadow: 0 4px 20px rgba(0, 0, 0, 0.06);
  animation: slideUp 0.6s ease-out 0.2s both;
}

.repair-header {
  display: flex;
  align-items: center;
  gap: 10px;
  font-size: 16px;
  font-weight: 600;
  color: #1a1a2e;
  margin-bottom: 20px;
  padding-bottom: 16px;
  border-bottom: 1px solid #e9ecef;
}

.repair-form {
  display: flex;
  flex-direction: column;
  gap: 16px;
}

.form-group {
  display: flex;
  flex-direction: column;
  gap: 8px;
}

.form-group label {
  font-size: 14px;
  font-weight: 500;
  color: #495057;
}

.form-group input,
.form-group select,
.form-group textarea {
  padding: 12px 16px;
  border: 1px solid #e9ecef;
  border-radius: 10px;
  font-size: 14px;
  color: #1a1a2e;
  transition: all 0.3s ease;
  font-family: inherit;
}

.form-group input:focus,
.form-group select:focus,
.form-group textarea:focus {
  outline: none;
  border-color: #4facfe;
  box-shadow: 0 0 0 3px rgba(79, 172, 254, 0.1);
}

.form-group input::placeholder,
.form-group textarea::placeholder {
  color: #adb5bd;
}

.submit-btn {
  display: flex;
  align-items: center;
  justify-content: center;
  gap: 8px;
  padding: 14px 24px;
  background: linear-gradient(135deg, #4facfe 0%, #00f2fe 100%);
  border: none;
  border-radius: 12px;
  color: white;
  font-size: 15px;
  font-weight: 600;
  cursor: pointer;
  transition: all 0.3s ease;
  box-shadow: 0 4px 12px rgba(79, 172, 254, 0.3);
  margin-top: 8px;
}

.submit-btn:hover {
  transform: translateY(-2px);
  box-shadow: 0 6px 16px rgba(79, 172, 254, 0.4);
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
  letter-spacing: 0;
}

.back-btn:hover {
  transform: translateX(-3px) scale(1.03);
  box-shadow: 0 6px 18px rgba(102, 126, 234, 0.4);
}

.back-btn:active {
  transform: translateX(-1px) scale(0.98);
}

/* Toast 提示 */
.toast {
  position: fixed;
  top: 50%;
  left: 50%;
  transform: translate(-50%, -50%);
  background: rgba(0, 0, 0, 0.8);
  color: white;
  padding: 12px 24px;
  border-radius: 8px;
  font-size: 14px;
  z-index: 1000;
  animation: fadeIn 0.3s ease;
}

@keyframes fadeIn {
  from { opacity: 0; transform: translate(-50%, -50%) scale(0.9); }
  to { opacity: 1; transform: translate(-50%, -50%) scale(1); }
}
</style>
