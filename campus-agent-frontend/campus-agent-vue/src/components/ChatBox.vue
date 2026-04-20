<template>
  <div class="chat-box d-flex flex-column">
    <!-- 顶部标题栏 -->
    <div class="chat-header">
      <div class="header-left">
        <div class="header-avatar">
          <svg xmlns="http://www.w3.org/2000/svg" width="18" height="18" fill="currentColor" viewBox="0 0 16 16">
            <path d="M8 16a6 6 0 0 0 6-6c0-1.655-1.122-2.904-2.432-4.362C10.254 4.176 8.75 2.503 8 0c-.75 2.503-2.254 4.176-3.568 5.638C3.122 7.096 2 8.345 2 10a6 6 0 0 0 6 6z"/>
          </svg>
        </div>
        <div>
          <div class="header-name">校园生活小管家</div>
          <div class="header-status"><span class="status-dot"></span>在线服务中</div>
        </div>
      </div>
      <div class="header-actions">
        <button class="header-btn" type="button" title="导出聊天记录" @click="exportChat">
          <svg xmlns="http://www.w3.org/2000/svg" width="15" height="15" fill="currentColor" viewBox="0 0 16 16">
            <path d="M.5 9.9a.5.5 0 0 1 .5.5v2.5a1 1 0 0 0 1 1h12a1 1 0 0 0 1-1v-2.5a.5.5 0 0 1 1 0v2.5a2 2 0 0 1-2 2H2a2 2 0 0 1-2-2v-2.5a.5.5 0 0 1 .5-.5z"/>
            <path d="M7.646 11.854a.5.5 0 0 0 .708 0l3-3a.5.5 0 0 0-.708-.708L8.5 10.293V1.5a.5.5 0 0 0-1 0v8.793L5.354 8.146a.5.5 0 1 0-.708.708l3 3z"/>
          </svg>
        </button>
        <button class="header-btn danger" type="button" title="清空聊天记录" @click="clearChat">
          <svg xmlns="http://www.w3.org/2000/svg" width="15" height="15" fill="currentColor" viewBox="0 0 16 16">
            <path d="M5.5 5.5A.5.5 0 0 1 6 6v6a.5.5 0 0 1-1 0V6a.5.5 0 0 1 .5-.5zm2.5 0a.5.5 0 0 1 .5.5v6a.5.5 0 0 1-1 0V6a.5.5 0 0 1 .5-.5zm3 .5a.5.5 0 0 0-1 0v6a.5.5 0 0 0 1 0V6z"/>
            <path fill-rule="evenodd" d="M14.5 3a1 1 0 0 1-1 1H13v9a2 2 0 0 1-2 2H5a2 2 0 0 1-2-2V4h-.5a1 1 0 0 1-1-1V2a1 1 0 0 1 1-1H6a1 1 0 0 1 1-1h2a1 1 0 0 1 1 1h3.5a1 1 0 0 1 1 1v1zM4.118 4 4 4.059V13a1 1 0 0 0 1 1h6a1 1 0 0 0 1-1V4.059L11.882 4H4.118zM2.5 3V2h11v1h-11z"/>
          </svg>
        </button>
      </div>
    </div>

    <!-- 欢迎区域 -->
    <div class="welcome-area">
      <!-- 收起时显示的展开条 -->
      <div v-if="!welcomeVisible" class="welcome-expand-bar" @click="toggleWelcome">
        <svg xmlns="http://www.w3.org/2000/svg" width="14" height="14" fill="currentColor" viewBox="0 0 16 16">
          <path d="M8 4a.5.5 0 0 1 .5.5v3h3a.5.5 0 0 1 0 1h-3v3a.5.5 0 0 1-1 0v-3h-3a.5.5 0 0 1 0-1h3v-3A.5.5 0 0 1 8 4z"/>
        </svg>
        <span>展开欢迎卡片</span>
      </div>

      <!-- 欢迎卡片（展开时显示） -->
      <div v-if="welcomeVisible" class="welcome-card">
        <div class="welcome-card-inner" style="position: relative;">
          <div class="welcome-header">
            <div class="welcome-avatar">
              <svg xmlns="http://www.w3.org/2000/svg" width="24" height="24" fill="currentColor" viewBox="0 0 16 16">
                <path d="M8 16a6 6 0 0 0 6-6c0-1.655-1.122-2.904-2.432-4.362C10.254 4.176 8.75 2.503 8 0c-.75 2.503-2.254 4.176-3.568 5.638C3.122 7.096 2 8.345 2 10a6 6 0 0 0 6 6z"/>
              </svg>
            </div>
            <div class="welcome-text">
              <h3>你好，我是校园小管家 ✨</h3>
              <p>点击快捷入口，或直接输入问题</p>
            </div>
          </div>
          <div class="quick-actions">
            <div class="quick-btn" @click="goToPage('/schedule')">
              <div class="quick-icon-wrap icon-purple">
                <svg xmlns="http://www.w3.org/2000/svg" width="18" height="18" fill="currentColor" viewBox="0 0 16 16">
                  <path d="M3.5 0a.5.5 0 0 1 .5.5V1h8V.5a.5.5 0 0 1 1 0V1h1a2 2 0 0 1 2 2v11a2 2 0 0 1-2 2H2a2 2 0 0 1-2-2V3a2 2 0 0 1 2-2h1V.5a.5.5 0 0 1 .5-.5zM1 4v10a1 1 0 0 0 1 1h12a1 1 0 0 0 1-1V4H1z"/>
                </svg>
              </div>
              <span class="quick-btn-label">今日课表</span>
            </div>
            <div class="quick-btn" @click="goToPage('/dining')">
              <div class="quick-icon-wrap icon-orange">
                <svg xmlns="http://www.w3.org/2000/svg" width="18" height="18" fill="currentColor" viewBox="0 0 16 16">
                  <path d="M3 2.5a.5.5 0 0 1 .5-.5h5a.5.5 0 0 1 0 1h-5a.5.5 0 0 1-.5-.5zm0 2a.5.5 0 0 1 .5-.5h7a.5.5 0 0 1 0 1h-7a.5.5 0 0 1-.5-.5zm0 2a.5.5 0 0 1 .5-.5h9a.5.5 0 0 1 0 1h-9a.5.5 0 0 1-.5-.5z"/>
                </svg>
              </div>
              <span class="quick-btn-label">食堂推荐</span>
            </div>
            <div class="quick-btn" @click="goToPage('/repair')">
              <div class="quick-icon-wrap icon-green">
                <svg xmlns="http://www.w3.org/2000/svg" width="18" height="18" fill="currentColor" viewBox="0 0 16 16">
                  <path d="M9.605 1.241a.5.5 0 0 1 .227.58L9.51 5.447c.294.104.327.522.032.824-.245.248-.615-.03-.615-.03l-.002-.003a.5.5 0 1 1 .707-.707c.166.166.366.28.574.323.29.06.47-.194.47-.194l.003-.002a.5.5 0 0 1 .597.153z"/>
                </svg>
              </div>
              <span class="quick-btn-label">报修电话</span>
            </div>
            <div class="quick-btn" @click="goToPage('/remind')">
              <div class="quick-icon-wrap icon-pink">
                <svg xmlns="http://www.w3.org/2000/svg" width="18" height="18" fill="currentColor" viewBox="0 0 16 16">
                  <path d="M8 3.5a.5.5 0 0 0-1 0V9a.5.5 0 0 0 .252.434l3.5 2a.5.5 0 0 0 .496-.868L8 8.71V3.5z"/>
                  <path d="M8 16A8 8 0 1 0 8 0a8 8 0 0 0 0 16zm7-8A7 7 0 1 1 1 8a7 7 0 0 1 14 0z"/>
                </svg>
              </div>
              <span class="quick-btn-label">提醒事项</span>
            </div>
          </div>
          <!-- 收起按钮 -->
          <button class="collapse-btn" type="button" @click="toggleWelcome" title="收起">
            <svg xmlns="http://www.w3.org/2000/svg" width="14" height="14" fill="currentColor" viewBox="0 0 16 16">
              <path d="M3 8a.5.5 0 0 1 .5-.5h9a.5.5 0 0 1 0 1h-9A.5.5 0 0 1 3 8z"/>
            </svg>
          </button>
        </div>
      </div>
    </div>

    <!-- 对话内容区域 -->
    <div class="chat-content flex-grow-1 overflow-auto" id="chatContent">
      <!-- 对话消息列表 -->
      <div v-for="(msg, index) in msgList" :key="index" class="message-wrapper mb-3" :class="msg.isUser ? 'user-msg' : 'ai-msg'">
        <div v-if="!msg.isUser" class="ai-avatar-sm me-2">
          <svg xmlns="http://www.w3.org/2000/svg" width="15" height="15" fill="currentColor" viewBox="0 0 16 16">
            <path d="M8 16a6 6 0 0 0 6-6c0-1.655-1.122-2.904-2.432-4.362C10.254 4.176 8.75 2.503 8 0c-.75 2.503-2.254 4.176-3.568 5.638C3.122 7.096 2 8.345 2 10a6 6 0 0 0 6 6z"/>
          </svg>
        </div>
        <div class="message-bubble rounded-3 p-3" :class="msg.isUser ? 'user-bubble' : 'ai-bubble'">
          <template v-if="msg.isUser">
            <div v-if="msg.images && msg.images.length > 0" class="mb-2 d-flex flex-wrap gap-2">
              <img v-for="(img, idx) in msg.images" :key="idx" :src="img" class="rounded msg-img" @click="previewImage(img)" />
            </div>
            <div v-if="msg.docs && msg.docs.length > 0" class="mb-2">
              <div v-for="(doc, idx) in msg.docs" :key="idx" class="msg-doc d-flex align-items-center gap-2 rounded px-3 py-2 mb-1">
                <div class="doc-icon-sm" :style="{ background: getDocColor(doc.name) }">{{ getDocExt(doc.name) }}</div>
                <span class="text-truncate" style="max-width: 150px;">{{ doc.name }}</span>
              </div>
            </div>
            <div>{{ msg.content }}</div>
          </template>
          <div v-else class="ai-msg-content" v-html="renderMarkdown(msg.content)"></div>
          <ResultCard v-if="msg.data" :data="msg.data" :intent="msg.intent" />
        </div>
      </div>

      <!-- 图片预览模态框 -->
      <div v-if="previewSrc" class="preview-modal" @click.self="previewSrc = null">
        <img :src="previewSrc" class="preview-image" @click="previewSrc = null" />
        <button type="button" class="preview-close" @click="previewSrc = null">
          <svg xmlns="http://www.w3.org/2000/svg" width="24" height="24" fill="currentColor" viewBox="0 0 16 16">
            <path d="M4.646 4.646a.5.5 0 0 1 .708 0L8 7.293l2.646-2.647a.5.5 0 0 1 .708.708L8.707 8l2.647 2.646a.5.5 0 0 1-.708.708L8 8.707l-2.646 2.647a.5.5 0 0 1-.708-.708L7.293 8 4.646 5.354a.5.5 0 0 1 0-.708z"/>
          </svg>
        </button>
      </div>
    </div>

    <!-- 已选文件预览区 -->
    <div v-if="selectedImages.length > 0 || selectedDocs.length > 0" class="file-preview-bar">
      <div class="d-flex flex-wrap gap-2 align-items-center">
        <div v-for="(img, index) in selectedImages" :key="'img-'+index" class="position-relative img-preview-card">
          <img :src="img" class="rounded-2" style="width: 56px; height: 56px; object-fit: cover; cursor: pointer;" @click="previewImage(img)" />
          <button type="button" class="btn-close position-absolute top-0 end-0 translate-middle" style="font-size: 10px; background: #fff; border-radius: 50%; padding: 3px; box-shadow: 0 2px 4px rgba(0,0,0,0.2);" @click="removeImage(index)"></button>
        </div>
        <div v-for="(doc, index) in selectedDocs" :key="'doc-'+index" class="doc-preview-card d-flex align-items-center gap-2 px-2 py-1 bg-white rounded-2 shadow-sm">
          <div class="doc-icon rounded-1 d-flex align-items-center justify-content-center" :style="{ background: getDocColor(doc.name) }">
            <span class="text-white fw-bold" style="font-size: 10px;">{{ getDocExt(doc.name) }}</span>
          </div>
          <span class="text-truncate" style="max-width: 100px; font-size: 12px; color: #495057;">{{ doc.name }}</span>
          <button type="button" class="btn-close" style="font-size: 9px;" @click="removeDoc(index)"></button>
        </div>
      </div>
    </div>

    <!-- 底部输入区域 -->
    <div class="input-area">
      <div class="input-inner">
        <!-- 上传按钮 -->
        <div class="position-relative">
          <button class="upload-btn" type="button" title="上传图片或文件" @click="triggerUpload">
            <svg xmlns="http://www.w3.org/2000/svg" width="16" height="16" fill="currentColor" viewBox="0 0 16 16">
              <path d="M8 4a.5.5 0 0 1 .5.5v3h3a.5.5 0 0 1 0 1h-3v3a.5.5 0 0 1-1 0v-3h-3a.5.5 0 0 1 0-1h3v-3A.5.5 0 0 1 8 4z"/>
            </svg>
          </button>
          <span v-if="totalSelectedCount > 0" class="badge-count">{{ totalSelectedCount }}</span>
        </div>
        <input ref="uploadInput" type="file" multiple accept="image/*,.doc,.docx,.xls,.xlsx,.ppt,.pptx,.pdf" style="display: none;" @change="handleFileUpload" />
        <!-- 输入框 -->
        <input v-model="inputContent" type="text" class="chat-input flex-grow-1" placeholder="有什么想问的，尽管说…" @keyup.enter="sendMsg">
        <!-- 发送按钮 -->
        <button class="send-btn" type="button" @click="sendMsg">
          <svg xmlns="http://www.w3.org/2000/svg" width="17" height="17" fill="currentColor" viewBox="0 0 16 16">
            <path d="M15.854.146a.5.5 0 0 1 .11.54l-5.819 14.547a.75.75 0 0 1-1.329.124l-3.178-4.995L.643 7.184a.75.75 0 0 1 .124-1.33L15.314.037a.5.5 0 0 1 .54.11ZM6.636 10.07l2.761 4.338L14.13 2.576 6.636 10.07Zm6.787-8.201L1.591 6.602l4.339 2.76 7.493-7.493Z"/>
          </svg>
        </button>
      </div>
    </div>
  </div>
</template>

<script>
import ResultCard from './ResultCard.vue'
export default {
  name: 'ChatBox',
  components: { ResultCard },
  data() {
    return {
      welcomeVisible: true,  // 欢迎卡片显示状态
      inputContent: '',      // 输入内容
      msgList: [],           // 消息列表
      userId: 1,             // 测试用户ID，可扩展为登录后获取
      selectedImages: [],    // 已选图片数组（base64，未发送）
      selectedDocs: [],      // 已选文档数组 {name, base64}
      previewSrc: null,      // 图片预览模态框
      isRecording: false,   // 语音录音状态
      mediaRecorder: null,  // 媒体录制器
      audioChunks: []       // 录音数据块
    }
  },
  computed: {
    // 已选文件总数
    totalSelectedCount() {
      return this.selectedImages.length + this.selectedDocs.length
    }
  },
  mounted() {
    // 存储测试用户ID到Cookies
    this.$cookies.set('userId', this.userId, '7d')
    // 从 localStorage 恢复输入内容
    const savedInput = localStorage.getItem('chatInput_' + this.userId)
    if (savedInput) this.inputContent = savedInput
    // 从 localStorage 恢复欢迎卡片状态
    const savedWelcome = localStorage.getItem('welcomeVisible_' + this.userId)
    if (savedWelcome !== null) this.welcomeVisible = savedWelcome === 'true'
    // 从 localStorage 恢复消息列表
    const savedMsgs = localStorage.getItem('chatMsgs_' + this.userId)
    if (savedMsgs) {
      try { this.msgList = JSON.parse(savedMsgs) } catch (e) {}
    }
    // 加载服务端历史（仅首次加载时用，localStorage 有数据则跳过）
    if (!savedMsgs) this.loadHistory()
  },
  watch: {
    // 输入内容实时缓存
    inputContent(val) {
      localStorage.setItem('chatInput_' + this.userId, val)
    },
    // 消息列表变化时缓存
    msgList: {
      handler(val) {
        localStorage.setItem('chatMsgs_' + this.userId, JSON.stringify(val))
      },
      deep: true
    }
  },
methods: {
    // 切换欢迎卡片显示/收起
    toggleWelcome() {
      this.welcomeVisible = !this.welcomeVisible
      localStorage.setItem('welcomeVisible_' + this.userId, this.welcomeVisible)
    },
    // 加载对话历史
    async loadHistory() {
      try {
        const res = await this.$axios.get('/agent/history', {
          params: { userId: this.userId }
        })
        if (res.data.code === 200 && res.data.data) {
          // 将历史消息添加到消息列表
          res.data.data.forEach(msg => {
            this.msgList.push({
              isUser: msg.role === 'user',
              content: msg.content,
              images: [],
              docs: []
            })
          })
        }
      } catch (err) {
        console.error('加载历史失败:', err)
      }
    },
    // 触发上传（统一入口）
    triggerUpload() {
      this.$refs.uploadInput.click()
    },
    // 处理文件上传（图片+文档）
    handleFileUpload(event) {
      const files = event.target.files
      if (!files || files.length === 0) return
      
      let imageCount = this.selectedImages.length
      let docCount = this.selectedDocs.length
      
      for (let i = 0; i < files.length; i++) {
        const file = files[i]
        
        // 最多4张图片
        if (file.type.startsWith('image/') && imageCount < 4) {
          this.compressImage(file, 800, (base64) => {
            this.selectedImages.push(base64)
          })
          imageCount++
        }
        // 最多2个文档
        else if (!file.type.startsWith('image/') && docCount < 2) {
          this.convertFileToBase64(file, (base64) => {
            this.selectedDocs.push({ name: file.name, base64: base64 })
          })
          docCount++
        }
        // 超出限制时提示
        else if (file.type.startsWith('image/') && imageCount >= 4) {
          this.$message && this.$message.warning('最多只能上传4张图片')
          break
        }
        else if (docCount >= 2) {
          this.$message && this.$message.warning('最多只能上传2个文档')
          break
        }
      }
      
      // 文件大小校验（图片10MB，文档20MB）
      for (let i = 0; i < files.length; i++) {
        const file = files[i]
        if (file.type.startsWith('image/') && file.size > 10 * 1024 * 1024) {
          this.$message && this.$message.warning('图片大小不能超过10MB')
          return
        }
        if (!file.type.startsWith('image/') && file.size > 20 * 1024 * 1024) {
          this.$message && this.$message.warning('文件大小不能超过20MB')
          return
        }
      }
      
      event.target.value = ''
    },
    // 移除已选图片
    removeImage(index) {
      this.selectedImages.splice(index, 1)
    },
    // 移除已选文档
    removeDoc(index) {
      this.selectedDocs.splice(index, 1)
    },
    // 获取文档扩展名
    getDocExt(filename) {
      const ext = filename.split('.').pop()?.toUpperCase() || 'DOC'
      return ext.substring(0, 3)
    },
    // 获取文档图标颜色
    getDocColor(filename) {
      const ext = filename.split('.').pop()?.toLowerCase() || ''
      const colors = {
        pdf: '#dc3545',
        doc: '#0d6efd',
        docx: '#0d6efd',
        xls: '#198754',
        xlsx: '#198754',
        ppt: '#fd7e14',
        pptx: '#fd7e14'
      }
      return colors[ext] || '#6c757d'
    },
    // 文件转Base64
    convertFileToBase64(file, callback) {
      const reader = new FileReader()
      reader.onload = (e) => callback(e.target.result)
      reader.readAsDataURL(file)
    },
    // 压缩图片到指定宽度，返回base64
    compressImage(file, maxWidth = 800, callback) {
      const reader = new FileReader()
      reader.onload = (e) => {
        const img = new Image()
        img.onload = () => {
          const canvas = document.createElement('canvas')
          let width = img.width
          let height = img.height
          
          // 如果图片已经小于最大宽度，直接返回
          if (width <= maxWidth) {
            callback(e.target.result)
            return
          }
          
          // 按比例缩放
          const ratio = maxWidth / width
          width = maxWidth
          height = Math.round(height * ratio)
          
          canvas.width = width
          canvas.height = height
          const ctx = canvas.getContext('2d')
          ctx.drawImage(img, 0, 0, width, height)
          
          // 输出压缩后的base64（质量0.7）
          const compressed = canvas.toDataURL('image/jpeg', 0.7)
          callback(compressed)
        }
        img.src = e.target.result
      }
      reader.readAsDataURL(file)
    },
    // 语音输入（录音转文字需要后端ASR，这里仅做录音演示）
    async toggleVoice() {
      if (this.isRecording) {
        // 停止录音
        if (this.mediaRecorder) {
          this.mediaRecorder.stop()
          this.mediaRecorder.onstop = () => {
            this.audioChunks = []
            alert('语音已录制（ASR语音转文字功能待接入）')
          }
        }
        this.isRecording = false
      } else {
        // 开始录音
        try {
          const stream = await navigator.mediaDevices.getUserMedia({ audio: true })
          this.mediaRecorder = new MediaRecorder(stream)
          this.audioChunks = []
          this.mediaRecorder.ondataavailable = (e) => {
            if (e.data.size > 0) this.audioChunks.push(e.data)
          }
          this.mediaRecorder.start()
          this.isRecording = true
        } catch (err) {
          alert('请允许麦克风权限后使用语音输入')
          console.error(err)
        }
      }
    },
    // 清除对话历史
    clearHistory() {
      if (confirm('确定要清除当前对话历史吗？')) {
        this.msgList = []
        // 调用后端清除历史
        this.$axios.post('/agent/clear-history', null, {
          params: { userId: this.userId }
        }).then(() => {
          this.$message && this.$message.success('对话历史已清除')
        }).catch(() => {
          // 后端未实现清除接口时静默失败
        })
      }
    },
    // 跳转到指定页面
    goToPage(path) {
      this.$router.push(path)
    },
    // 预览大图
    previewImage(src) {
      this.previewSrc = src
    },
    // 导出聊天记录
    async exportChat() {
      try {
        const res = await this.$axios.get('/agent/export', {
          params: { userId: this.userId, format: 'md' }
        })
        if (res.data.code === 200) {
          const { content, filename } = res.data.data
          // 创建下载
          const blob = new Blob([content], { type: 'text/markdown;charset=utf-8' })
          const url = URL.createObjectURL(blob)
          const link = document.createElement('a')
          link.href = url
          link.download = filename
          link.click()
          URL.revokeObjectURL(url)
          this.$message && this.$message.success('导出成功！')
        }
      } catch (err) {
        console.error('导出失败:', err)
        this.$message && this.$message.error('导出失败，请重试')
      }
    },
    // 清空聊天记录
    async clearChat() {
      if (!confirm('确定要清空所有聊天记录吗？此操作不可恢复。')) return
      try {
        const res = await this.$axios.post('/agent/clear-history', null, {
          params: { userId: this.userId }
        })
        if (res.data.code === 200) {
          this.msgList = []
          this.$message && this.$message.success('聊天记录已清空')
        }
      } catch (err) {
        console.error('清空失败:', err)
        this.$message && this.$message.error('清空失败，请重试')
      }
    },
    // 轻量markdown渲染（纯文本时原样输出，有markdown符号时转为HTML）
    renderMarkdown(text) {
      if (!text) return ''
      let html = text
        // 转义HTML特殊字符，防止XSS
        .replace(/&/g, '&amp;')
        .replace(/</g, '&lt;')
        .replace(/>/g, '&gt;')
      // 检查是否包含markdown标记
      const hasMd = /[#*>\-_~[!]/.test(text)
      if (!hasMd) return text  // 纯文本直接返回，不包裹html标签
      // 有markdown → 逐步转换
      html = html
        // 代码块 ```code``` → <pre><code>
        .replace(/```([\s\S]*?)```/g, '<pre><code>$1</code></pre>')
        // 行内代码 `code` → <code>
        .replace(/`([^`]+)`/g, '<code>$1</code>')
        // 标题 ### → <h3> (支持h1-h4)
        .replace(/^#### (.+)$/gm, '<h4>$1</h4>')
        .replace(/^### (.+)$/gm, '<h3>$1</h3>')
        .replace(/^## (.+)$/gm, '<h2>$1</h2>')
        .replace(/^# (.+)$/gm, '<h1>$1</h1>')
        // 粗斜体 ***text*** → <strong><em>
        .replace(/\*\*\*(.+?)\*\*\*/g, '<strong><em>$1</em></strong>')
        // 粗体 **text** → <strong>
        .replace(/\*\*(.+?)\*\*/g, '<strong>$1</strong>')
        // 斜体 *text* → <em>（排除已处理的粗体）
        .replace(/(?<!\*)\*([^*]+)\*(?!\*)/g, '<em>$1</em>')
        // 引用 > text → <blockquote>
        .replace(/^&gt; (.+)$/gm, '<blockquote>$1</blockquote>')
        // 无序列表 - item / * item
        .replace(/^[-*] (.+)$/gm, '<li>$1</li>')
        // 连续的<li>用<ul>包裹（简单处理）
        .replace(/(<li>.*<\/li>(\r?\n)?)+/g, '<ul>$&</ul>')
        // 段落分隔：两个以上换行
        .replace(/\n\n+/g, '</p><p>')
        // 单个换行 → <br>
        .replace(/\n/g, '<br>')
      return '<p>' + html + '</p>'
    },
    // 发送消息
    async sendMsg() {
      const text = this.inputContent.trim()
      const hasImages = this.selectedImages.length > 0
      const hasDocs = this.selectedDocs.length > 0
      
      if (!text && !hasImages && !hasDocs) {
        this.$message && this.$message.warning('请输入内容或选择图片/文件！')
        return
      }
      
      // 1. 添加用户消息到列表（带图片和文档预览）
      this.msgList.push({
        isUser: true,
        content: text || (hasDocs ? '(文件)' : '(图片)'),
        images: [...this.selectedImages],
        docs: [...this.selectedDocs]
      })
      
      const content = text
      const images = [...this.selectedImages]
      const docs = [...this.selectedDocs]
      
      this.inputContent = ''
      this.selectedImages = []
      this.selectedDocs = []
      
      // 2. 滚动到底部
      this.$nextTick(() => {
        const chatContent = document.getElementById('chatContent')
        chatContent.scrollTop = chatContent.scrollHeight
      })
      
      // 3. 调用后端接口（区分有图/有文档/纯文本）
      try {
        let res
        if (hasImages) {
          // 有图片：使用VL模型（支持看图对话）
          const formData = new FormData()
          formData.append('content', content)
          formData.append('userId', this.userId)
          formData.append('imageBase64', images[0])
          // 如果同时有文档，也传递文档
          if (hasDocs) {
            formData.append('docBase64', docs[0].base64)
            formData.append('docName', docs[0].name)
          }
          res = await this.$axios.post('/agent/chat-with-image', formData, {
            headers: { 'Content-Type': 'multipart/form-data' }
          })
        } else {
          // 纯文本或只有文档：使用普通对话
          if (hasDocs) {
            // 有文档：用FormData传递
            const formData = new FormData()
            formData.append('content', content || '')
            formData.append('userId', this.userId)
            formData.append('docBase64', docs[0].base64)
            formData.append('docName', docs[0].name)
            res = await this.$axios.post('/agent/chat', formData, {
              headers: { 'Content-Type': 'multipart/form-data' }
            })
          } else {
            // 纯文本
            res = await this.$axios.post('/agent/chat', null, {
              params: {
                content: content,
                userId: this.userId
              }
            })
          }
        }
        if (res.data.code === 200) {
          // 4. 添加机器人消息到列表
          this.msgList.push({
            isUser: false,
            content: res.data.data.reply,
            data: res.data.data.data,
            intent: res.data.data.intent
          })
        } else {
          this.msgList.push({
            isUser: false,
            content: '服务出错了：' + res.data.msg
          })
        }
      } catch (err) {
        console.error(err)
        this.msgList.push({
          isUser: false,
          content: '网络出错了，请重试！'
        })
      } finally {
        // 5. 滚动到底部
        this.$nextTick(() => {
          const chatContent = document.getElementById('chatContent')
          chatContent.scrollTop = chatContent.scrollHeight
        })
      }
    }
  }
}
</script>

<style scoped>
/* ===== 整体容器 ===== */
.chat-box {
  background: white;
  height: 600px;
  display: flex;
  flex-direction: column;
  border-radius: 20px;
  overflow: hidden;
  box-shadow: 0 8px 32px rgba(102, 126, 234, 0.12);
}

/* ===== 顶部标题栏 ===== */
.chat-header {
  display: flex;
  align-items: center;
  justify-content: space-between;
  padding: 16px 20px;
  border-bottom: 1px solid #f0f2f8;
  background: white;
}
.header-left {
  display: flex;
  align-items: center;
  gap: 12px;
}
.header-avatar {
  width: 40px;
  height: 40px;
  min-width: 40px;
  background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
  border-radius: 12px;
  display: flex;
  align-items: center;
  justify-content: center;
  color: white;
}
.header-name {
  font-size: 15px;
  font-weight: 600;
  color: #1a1a2e;
}
.header-status {
  font-size: 12px;
  color: #9ca3af;
  display: flex;
  align-items: center;
  gap: 5px;
  margin-top: 2px;
}
.status-dot {
  width: 7px;
  height: 7px;
  background: #34d399;
  border-radius: 50%;
}
.header-actions {
  display: flex;
  gap: 6px;
}
.header-btn {
  width: 32px;
  height: 32px;
  border: 1px solid #f0f2f8;
  border-radius: 8px;
  background: white;
  color: #9ca3af;
  display: flex;
  align-items: center;
  justify-content: center;
  cursor: pointer;
  transition: all 0.2s;
}
.header-btn:hover {
  border-color: #667eea;
  color: #667eea;
}
.header-btn.danger:hover {
  border-color: #f87171;
  color: #f87171;
}

/* ===== 欢迎区域 ===== */
.welcome-area {
  padding: 16px 20px 20px;
}
.welcome-expand-bar {
  padding: 10px 16px;
  border-radius: 12px;
  background: #f8f9ff;
  color: #667eea;
  font-size: 13px;
  display: flex;
  align-items: center;
  justify-content: center;
  gap: 6px;
  cursor: pointer;
  transition: all 0.2s;
  border: 1px dashed #c4c9f0;
}
.welcome-expand-bar:hover {
  background: #f0f2ff;
  border-color: #667eea;
}
.welcome-card {
  border-radius: 16px;
  background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
  overflow: hidden;
  animation: cardSlideIn 0.4s ease;
}
@keyframes cardSlideIn {
  from { opacity: 0; transform: translateY(-10px); }
  to { opacity: 1; transform: translateY(0); }
}
.welcome-card-inner {
  padding: 24px 20px 20px;
}
.welcome-header {
  display: flex;
  align-items: center;
  gap: 12px;
  margin-bottom: 16px;
}
.welcome-avatar {
  width: 48px;
  height: 48px;
  background: rgba(255,255,255,0.2);
  border-radius: 14px;
  display: flex;
  align-items: center;
  justify-content: center;
  color: white;
}
.welcome-text h3 {
  font-size: 16px;
  font-weight: 600;
  color: white;
  margin: 0 0 4px;
}
.welcome-text p {
  font-size: 12px;
  color: rgba(255,255,255,0.75);
  margin: 0;
}
.collapse-btn {
  position: absolute;
  top: 16px;
  right: 16px;
  width: 28px;
  height: 28px;
  border: none;
  border-radius: 8px;
  background: rgba(255,255,255,0.2);
  color: white;
  display: flex;
  align-items: center;
  justify-content: center;
  cursor: pointer;
  transition: all 0.2s;
}
.collapse-btn:hover {
  background: rgba(255,255,255,0.35);
}
.quick-actions {
  display: grid;
  grid-template-columns: repeat(4, 1fr);
  gap: 10px;
}
.quick-btn {
  display: flex;
  flex-direction: column;
  align-items: center;
  gap: 8px;
  padding: 14px 8px;
  background: rgba(255,255,255,0.15);
  border: 1px solid rgba(255,255,255,0.25);
  border-radius: 12px;
  color: white;
  cursor: pointer;
  transition: all 0.2s;
}
.quick-btn:hover {
  background: rgba(255,255,255,0.25);
  transform: translateY(-2px);
}
.quick-icon-wrap {
  width: 36px;
  height: 36px;
  border-radius: 10px;
  display: flex;
  align-items: center;
  justify-content: center;
  color: white;
}
.icon-purple { background: rgba(255,255,255,0.25); }
.icon-orange { background: rgba(255,255,255,0.25); }
.icon-green { background: rgba(255,255,255,0.25); }
.icon-pink { background: rgba(255,255,255,0.25); }
.quick-btn-label {
  font-size: 11px;
  font-weight: 500;
}

/* ===== 对话内容区 ===== */
.chat-content {
  flex: 1;
  overflow-y: auto;
  padding: 16px 20px;
  background: #f8f9ff;
}
.chat-content::-webkit-scrollbar {
  width: 4px;
}
.chat-content::-webkit-scrollbar-track {
  background: transparent;
}
.chat-content::-webkit-scrollbar-thumb {
  background: #e0e3f0;
  border-radius: 4px;
}

/* ===== 消息样式 ===== */
.message-wrapper {
  display: flex;
  align-items: flex-end;
  gap: 8px;
  margin-bottom: 12px;
  animation: msgSlide 0.3s ease;
}
@keyframes msgSlide {
  from { opacity: 0; transform: translateY(8px); }
  to { opacity: 1; transform: translateY(0); }
}
.user-msg { justify-content: flex-end; }
.ai-msg { justify-content: flex-start; }

.ai-avatar-sm {
  width: 32px;
  height: 32px;
  min-width: 32px;
  background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
  border-radius: 10px;
  display: flex;
  align-items: center;
  justify-content: center;
  color: white;
}
.message-bubble {
  max-width: 75%;
  font-size: 14px;
  line-height: 1.6;
  animation: bubbleIn 0.2s ease;
}
@keyframes bubbleIn {
  from { opacity: 0; transform: scale(0.95); }
  to { opacity: 1; transform: scale(1); }
}
.user-bubble {
  background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
  color: white;
  border-radius: 14px 14px 4px 14px;
  padding: 10px 14px;
  font-size: 13px;
}
.ai-bubble {
  background: white;
  border: 1px solid #ebe9f5;
  border-radius: 14px 14px 14px 4px;
  padding: 10px 14px;
  color: #374151;
  font-size: 13px;
}

/* ===== 消息图片/文档 ===== */
.msg-img {
  max-width: 120px;
  max-height: 120px;
  object-fit: cover;
  cursor: pointer;
  border-radius: 8px;
  margin-top: 6px;
}
.msg-doc {
  background: rgba(255,255,255,0.15);
  font-size: 12px;
  border-radius: 8px;
  padding: 8px 12px;
}
.ai-msg .msg-doc { background: #f3f4f6; }
.doc-icon-sm {
  width: 24px;
  height: 24px;
  border-radius: 6px;
  display: flex;
  align-items: center;
  justify-content: center;
  font-size: 9px;
  font-weight: 700;
  color: white;
}

/* ===== 图片预览 ===== */
.preview-modal {
  position: fixed;
  top: 0; left: 0;
  width: 100%; height: 100%;
  background: rgba(0,0,0,0.85);
  z-index: 9999;
  display: flex;
  align-items: center;
  justify-content: center;
}
.preview-image {
  max-width: 90vw;
  max-height: 90vh;
  object-fit: contain;
  border-radius: 12px;
}
.preview-close {
  position: absolute;
  top: 20px; right: 20px;
  background: rgba(255,255,255,0.15);
  border: none;
  color: white;
  width: 40px; height: 40px;
  border-radius: 50%;
  display: flex;
  align-items: center;
  justify-content: center;
  cursor: pointer;
}

/* ===== 文件预览条 ===== */
.file-preview-bar {
  padding: 10px 20px;
  background: white;
  border-top: 1px solid #f0f2f8;
}

/* ===== 底部输入区 ===== */
.input-area {
  padding: 12px 20px 16px;
  background: white;
  border-top: 1px solid #f0f2f8;
}
.input-inner {
  display: flex;
  align-items: center;
  gap: 10px;
  background: #f5f6fa;
  border: 1px solid #e8eaf0;
  border-radius: 12px;
  padding: 8px 12px;
  transition: all 0.2s;
}
.input-inner:focus-within {
  background: white;
  border-color: #667eea;
  box-shadow: 0 0 0 3px rgba(102, 126, 234, 0.1);
}
.upload-btn {
  width: 36px;
  height: 36px;
  min-width: 36px;
  border: none;
  border-radius: 10px;
  background: #ebe9f5;
  color: #764ba2;
  display: flex;
  align-items: center;
  justify-content: center;
  cursor: pointer;
  transition: all 0.2s;
}
.upload-btn:hover {
  background: #ddd6fe;
}
.badge-count {
  position: absolute;
  top: -4px; right: -4px;
  min-width: 16px; height: 16px;
  background: #f87171;
  color: white;
  font-size: 10px;
  font-weight: bold;
  border-radius: 8px;
  display: flex;
  align-items: center;
  justify-content: center;
}
.chat-input {
  border: none;
  background: transparent;
  padding: 8px 4px;
  font-size: 14px;
  color: #374151;
  outline: none;
  flex: 1;
  min-width: 0;
}
.chat-input::placeholder {
  color: #aab0be;
}
.send-btn {
  width: 38px;
  height: 38px;
  min-width: 38px;
  border: none;
  border-radius: 10px;
  background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
  color: white;
  display: flex;
  align-items: center;
  justify-content: center;
  cursor: pointer;
  transition: all 0.2s;
}
.send-btn:hover {
  transform: scale(1.05);
  box-shadow: 0 4px 12px rgba(102, 126, 234, 0.35);
}
.send-btn:active {
  transform: scale(0.98);
}

/* ===== 文件预览卡片 ===== */
.img-preview-card {
  background: white;
  border-radius: 8px;
  padding: 3px;
}
.doc-icon {
  width: 28px; height: 28px;
  border-radius: 6px;
  display: flex;
  align-items: center;
  justify-content: center;
}

/* ===== AI消息 Markdown ===== */
.ai-msg-content :deep(h1),
.ai-msg-content :deep(h2),
.ai-msg-content :deep(h3),
.ai-msg-content :deep(h4) {
  font-size: 1em;
  font-weight: bold;
  margin: 0.3em 0;
}
.ai-msg-content :deep(strong) { font-weight: bold; }
.ai-msg-content :deep(em) { font-style: italic; }
.ai-msg-content :deep(code) {
  background: #f0f2f5;
  padding: 2px 6px;
  border-radius: 4px;
  font-size: 0.9em;
  color: #c7254e;
}
.ai-msg-content :deep(pre) {
  background: #1e293b;
  color: #e2e8f0;
  padding: 10px 14px;
  border-radius: 8px;
  overflow-x: auto;
  margin: 6px 0;
}
.ai-msg-content :deep(blockquote) {
  border-left: 3px solid #a5b4fc;
  padding-left: 10px;
  margin: 6px 0;
  color: #6b7280;
}
.ai-msg-content :deep(ul) { padding-left: 18px; margin: 4px 0; }
.ai-msg-content :deep(li) { margin: 2px 0; }
.ai-msg-content :deep(p) { margin: 2px 0; }
</style>
