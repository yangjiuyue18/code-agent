<template>
  <div class="chat-box h-100 d-flex flex-column">
    <!-- 对话内容区域 -->
    <div class="chat-content flex-grow-1 overflow-auto" id="chatContent">
      <!-- 机器人欢迎语 -->
      <div class="welcome-section d-flex mb-4 justify-content-start">
        <div class="ai-avatar me-3">
          <svg xmlns="http://www.w3.org/2000/svg" width="20" height="20" fill="currentColor" viewBox="0 0 16 16">
            <path d="M8 16a6 6 0 0 0 6-6c0-1.655-1.122-2.904-2.432-4.362C10.254 4.176 8.75 2.503 8 0c-.75 2.503-2.254 4.176-3.568 5.638C3.122 7.096 2 8.345 2 10a6 6 0 0 0 6 6z"/>
            <circle cx="8" cy="5" r="1.5"/>
          </svg>
        </div>
        <div class="welcome-card rounded-3 p-4">
          <div class="welcome-title mb-2">你好～我是校园生活小管家 ✨</div>
          <div class="welcome-text">有什么可以帮你的？试试问我：</div>
          <div class="quick-questions mt-3">
            <span class="quick-tag" @click="goToPage('/schedule')">📅 今日课表</span>
            <span class="quick-tag" @click="goToPage('/dining')">🍜 食堂推荐</span>
            <span class="quick-tag" @click="goToPage('/repair')">🔧 报修电话</span>
            <span class="quick-tag" @click="goToPage('/remind')">⏰ 提醒事项</span>
          </div>
        </div>
      </div>
    <!-- 对话消息列表 -->
    <div v-for="(msg, index) in msgList" :key="index" class="message-wrapper mb-3" :class="msg.isUser ? 'user-msg' : 'ai-msg'">
      <div v-if="!msg.isUser" class="ai-avatar-sm me-2">
        <svg xmlns="http://www.w3.org/2000/svg" width="16" height="16" fill="currentColor" viewBox="0 0 16 16">
          <path d="M8 16a6 6 0 0 0 6-6c0-1.655-1.122-2.904-2.432-4.362C10.254 4.176 8.75 2.503 8 0c-.75 2.503-2.254 4.176-3.568 5.638C3.122 7.096 2 8.345 2 10a6 6 0 0 0 6 6z"/>
        </svg>
      </div>
      <div class="message-bubble rounded-3 p-3" :class="msg.isUser ? 'user-bubble' : 'ai-bubble'">
        <!-- 用户消息：文本+图片预览+文档 -->
        <template v-if="msg.isUser">
          <!-- 图片预览（多图） -->
          <div v-if="msg.images && msg.images.length > 0" class="mb-2 d-flex flex-wrap gap-2">
            <img v-for="(img, idx) in msg.images" :key="idx" :src="img" class="rounded msg-img" @click="previewImage(img)" />
          </div>
          <!-- 文档预览 -->
          <div v-if="msg.docs && msg.docs.length > 0" class="mb-2">
            <div v-for="(doc, idx) in msg.docs" :key="idx" class="msg-doc d-flex align-items-center gap-2 rounded px-3 py-2 mb-1">
              <div class="doc-icon-sm" :style="{ background: getDocColor(doc.name) }">{{ getDocExt(doc.name) }}</div>
              <span class="text-truncate" style="max-width: 150px;">{{ doc.name }}</span>
            </div>
          </div>
          <div>{{ msg.content }}</div>
        </template>
        <div v-else class="ai-msg-content" v-html="renderMarkdown(msg.content)"></div>
        <!-- 结果卡片：课表/提醒/知识库 -->
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
    <div v-if="selectedImages.length > 0 || selectedDocs.length > 0" class="mb-3 p-3 bg-gradient rounded-3" style="background: linear-gradient(135deg, #f8f9fa 0%, #e9ecef 100%); border: 1px solid #dee2e6;">
      <div class="d-flex flex-wrap gap-3 align-items-center">
        <!-- 图片预览 -->
        <div v-for="(img, index) in selectedImages" :key="'img-'+index" class="position-relative img-preview-card">
          <img :src="img" class="rounded-2" style="width: 70px; height: 70px; object-fit: cover; cursor: pointer;" @click="previewImage(img)" />
          <button type="button" class="btn-close position-absolute top-0 end-0 translate-middle" style="font-size: 12px; background: #fff; border-radius: 50%; padding: 4px; box-shadow: 0 2px 4px rgba(0,0,0,0.2);" @click="removeImage(index)"></button>
        </div>
        <!-- 文档预览 -->
        <div v-for="(doc, index) in selectedDocs" :key="'doc-'+index" class="doc-preview-card d-flex align-items-center gap-2 px-3 py-2 bg-white rounded-2 shadow-sm">
          <div class="doc-icon rounded-1 d-flex align-items-center justify-content-center" :style="{ background: getDocColor(doc.name) }">
            <span class="text-white fw-bold" style="font-size: 10px;">{{ getDocExt(doc.name) }}</span>
          </div>
          <span class="text-truncate" style="max-width: 120px; font-size: 13px; color: #495057;">{{ doc.name }}</span>
          <button type="button" class="btn-close" style="font-size: 10px;" @click="removeDoc(index)"></button>
        </div>
      </div>
    </div>
    <!-- 输入框区域 -->
    <div class="input-wrapper d-flex align-items-end gap-3 p-3 bg-light rounded-3">
      <!-- 回形针上传按钮 -->
      <div class="position-relative">
        <button class="upload-btn" type="button" title="上传图片或文件" @click="triggerUpload">
          <svg xmlns="http://www.w3.org/2000/svg" width="20" height="20" fill="currentColor" viewBox="0 0 16 16">
            <path d="M4.5 3a2.5 2.5 0 0 1 5 0v9a1.5 1.5 0 0 1-3 0V5a.5.5 0 0 1 1 0v9a3 3 0 0 0 6 0V5a.5.5 0 0 1 1 0v9a4 4 0 0 1-8 0V3z"/>
            <path d="M9.5 1H4a2 2 0 0 0-2 2v9a2 2 0 0 0 2 2h5.5a.5.5 0 0 0 0-1H4a1 1 0 0 1-1-1V3a1 1 0 0 1 1-1h5.5a.5.5 0 0 0 0-1H9.5z"/>
          </svg>
        </button>
        <!-- 已选文件数量徽标 -->
        <span v-if="totalSelectedCount > 0" class="badge-count">{{ totalSelectedCount }}</span>
      </div>
      <!-- 隐藏的文件上传input -->
      <input ref="uploadInput" type="file" multiple accept="image/*,.doc,.docx,.xls,.xlsx,.ppt,.pptx,.pdf" style="display: none;" @change="handleFileUpload" />
      <!-- 输入框 -->
      <input v-model="inputContent" type="text" class="chat-input flex-grow-1" placeholder="输入消息，AI小助手为你服务~" @keyup.enter="sendMsg">
      <!-- 操作按钮组 -->
      <div class="d-flex gap-2">
        <!-- 导出记录按钮 -->
        <button class="action-btn" type="button" title="导出聊天记录" @click="exportChat">
          <svg xmlns="http://www.w3.org/2000/svg" width="16" height="16" fill="currentColor" viewBox="0 0 16 16">
            <path d="M.5 9.9a.5.5 0 0 1 .5.5v2.5a1 1 0 0 0 1 1h12a1 1 0 0 0 1-1v-2.5a.5.5 0 0 1 1 0v2.5a2 2 0 0 1-2 2H2a2 2 0 0 1-2-2v-2.5a.5.5 0 0 1 .5-.5z"/>
            <path d="M7.646 11.854a.5.5 0 0 0 .708 0l3-3a.5.5 0 0 0-.708-.708L8.5 10.293V1.5a.5.5 0 0 0-1 0v8.793L5.354 8.146a.5.5 0 1 0-.708.708l3 3z"/>
          </svg>
        </button>
        <!-- 清空记录按钮 -->
        <button class="action-btn danger" type="button" title="清空聊天记录" @click="clearChat">
          <svg xmlns="http://www.w3.org/2000/svg" width="16" height="16" fill="currentColor" viewBox="0 0 16 16">
            <path d="M5.5 5.5A.5.5 0 0 1 6 6v6a.5.5 0 0 1-1 0V6a.5.5 0 0 1 .5-.5zm2.5 0a.5.5 0 0 1 .5.5v6a.5.5 0 0 1-1 0V6a.5.5 0 0 1 .5-.5zm3 .5a.5.5 0 0 0-1 0v6a.5.5 0 0 0 1 0V6z"/>
            <path fill-rule="evenodd" d="M14.5 3a1 1 0 0 1-1 1H13v9a2 2 0 0 1-2 2H5a2 2 0 0 1-2-2V4h-.5a1 1 0 0 1-1-1V2a1 1 0 0 1 1-1H6a1 1 0 0 1 1-1h2a1 1 0 0 1 1 1h3.5a1 1 0 0 1 1 1v1zM4.118 4 4 4.059V13a1 1 0 0 0 1 1h6a1 1 0 0 0 1-1V4.059L11.882 4H4.118zM2.5 3V2h11v1h-11z"/>
          </svg>
        </button>
      </div>
      <!-- 发送按钮 -->
      <button class="send-btn" type="button" @click="sendMsg">
        <svg xmlns="http://www.w3.org/2000/svg" width="18" height="18" fill="currentColor" viewBox="0 0 16 16">
          <path d="M15.854.146a.5.5 0 0 1 .11.54l-5.819 14.547a.75.75 0 0 1-1.329.124l-3.178-4.995L.643 7.184a.75.75 0 0 1 .124-1.33L15.314.037a.5.5 0 0 1 .54.11ZM6.636 10.07l2.761 4.338L14.13 2.576 6.636 10.07Zm6.787-8.201L1.591 6.602l4.339 2.76 7.493-7.493Z"/>
        </svg>
      </button>
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
    // 加载对话历史
    this.loadHistory()
  },
  methods: {
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
            const audioBlob = new Blob(this.audioChunks, { type: 'audio/webm' })
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
      const hasMd = /[#*`>\-_~\[!]/.test(text)
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
        .replace(/^[\-*] (.+)$/gm, '<li>$1</li>')
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
.chat-box {
  background-color: transparent;
  height: 580px;
}
.chat-content {
  scroll-behavior: smooth;
  padding: 20px;
}

/* AI 头像 */
.ai-avatar {
  width: 44px;
  height: 44px;
  min-width: 44px;
  background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
  border-radius: 14px;
  display: flex;
  align-items: center;
  justify-content: center;
  color: white;
  box-shadow: 0 4px 12px rgba(102, 126, 234, 0.3);
}

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

/* 欢迎语卡片 */
.welcome-card {
  background: white;
  border: 1px solid #e9ecef;
  box-shadow: 0 4px 16px rgba(0, 0, 0, 0.06);
  max-width: 85%;
}

.welcome-title {
  font-size: 16px;
  font-weight: 600;
  color: #1a1a2e;
}

.welcome-text {
  font-size: 14px;
  color: #6b7280;
}

.quick-questions {
  display: flex;
  flex-wrap: wrap;
  gap: 8px;
}

.quick-tag {
  background: linear-gradient(135deg, #f8f9fa 0%, #e9ecef 100%);
  padding: 6px 12px;
  border-radius: 20px;
  font-size: 13px;
  color: #495057;
  cursor: pointer;
  transition: all 0.3s ease;
}

.quick-tag:hover {
  background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
  color: white;
  transform: translateY(-2px);
}

/* 消息样式 */
.message-wrapper {
  display: flex;
  align-items: flex-end;
  animation: msgSlide 0.3s ease-out;
}

@keyframes msgSlide {
  from {
    opacity: 0;
    transform: translateY(10px);
  }
  to {
    opacity: 1;
    transform: translateY(0);
  }
}

.user-msg {
  justify-content: flex-end;
}

.ai-msg {
  justify-content: flex-start;
}

.message-bubble {
  max-width: 75%;
  animation: bubbleIn 0.3s ease-out;
}

@keyframes bubbleIn {
  from {
    opacity: 0;
    transform: scale(0.95);
  }
  to {
    opacity: 1;
    transform: scale(1);
  }
}

.user-bubble {
  background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
  color: white;
  border-bottom-right-radius: 6px !important;
  box-shadow: 0 4px 12px rgba(102, 126, 234, 0.3);
}

.ai-bubble {
  background: white;
  border: 1px solid #e9ecef;
  border-bottom-left-radius: 6px !important;
  box-shadow: 0 4px 16px rgba(0, 0, 0, 0.06);
}

/* 消息图片 */
.msg-img {
  max-width: 140px;
  max-height: 140px;
  object-fit: cover;
  cursor: pointer;
  transition: transform 0.3s ease;
}

.msg-img:hover {
  transform: scale(1.05);
}

/* 消息文档 */
.msg-doc {
  background: rgba(255, 255, 255, 0.2);
  font-size: 12px;
}

.user-msg .msg-doc {
  background: rgba(255, 255, 255, 0.2);
}

.ai-msg .msg-doc {
  background: #f8f9fa;
}

.doc-icon-sm {
  width: 24px;
  height: 24px;
  border-radius: 4px;
  display: flex;
  align-items: center;
  justify-content: center;
  font-size: 8px;
  font-weight: 700;
  color: white;
}

/* 预览模态框 */
.preview-modal {
  position: fixed;
  top: 0;
  left: 0;
  width: 100%;
  height: 100%;
  background: rgba(0, 0, 0, 0.85);
  z-index: 9999;
  display: flex;
  align-items: center;
  justify-content: center;
  animation: fadeIn 0.2s ease;
}

@keyframes fadeIn {
  from { opacity: 0; }
  to { opacity: 1; }
}

.preview-image {
  max-width: 90vw;
  max-height: 90vh;
  object-fit: contain;
  border-radius: 12px;
  cursor: pointer;
}

.preview-close {
  position: absolute;
  top: 20px;
  right: 20px;
  background: rgba(255, 255, 255, 0.1);
  border: none;
  color: white;
  width: 44px;
  height: 44px;
  border-radius: 50%;
  display: flex;
  align-items: center;
  justify-content: center;
  cursor: pointer;
  transition: all 0.3s ease;
}

.preview-close:hover {
  background: rgba(255, 255, 255, 0.2);
  transform: scale(1.1);
}
/* AI消息markdown渲染样式 */
.ai-msg-content :deep(h1), .ai-msg-content :deep(h2),
.ai-msg-content :deep(h3), .ai-msg-content :deep(h4) {
  font-size: 1em;
  font-weight: bold;
  margin: 0.3em 0;
}
.ai-msg-content :deep(strong) { font-weight: bold; }
.ai-msg-content :deep(em) { font-style: italic; }
.ai-msg-content :deep(code) {
  background: #e9ecef;
  padding: 1px 4px;
  border-radius: 3px;
  font-size: 0.9em;
  color: #c7254e;
}
.ai-msg-content :deep(pre) {
  background: #212529;
  color: #f8f9fa;
  padding: 8px 12px;
  border-radius: 6px;
  overflow-x: auto;
  margin: 6px 0;
}
.ai-msg-content :deep(blockquote) {
  border-left: 3px solid #0d6efd;
  padding-left: 10px;
  margin: 6px 0;
  color: #495057;
}
.ai-msg-content :deep(ul) {
  padding-left: 18px;
  margin: 4px 0;
}
.ai-msg-content :deep(li) { margin: 2px 0; }
.ai-msg-content :deep(p) { margin: 2px 0; }
/* 扩展菜单样式 */
.dropdown-menu {
  border: none;
  border-radius: 12px;
  padding: 6px;
  box-shadow: 0 4px 16px rgba(0,0,0,0.12);
  font-size: 14px;
}
.dropdown-item {
  border-radius: 8px;
  transition: background 0.15s;
}
.dropdown-item:hover {
  background-color: #f0f2f5;
}
.dropdown-item:active {
  background-color: #e4e6eb;
}
.dropdown-divider {
  margin: 4px 0;
}
/* ========== 美化样式 ========== */
/* 输入框容器 */
.input-wrapper {
  background: linear-gradient(135deg, #ffffff 0%, #f8f9fa 100%);
  border: 1px solid #e9ecef;
  box-shadow: 0 2px 8px rgba(0, 0, 0, 0.04);
  transition: all 0.3s ease;
}
.input-wrapper:focus-within {
  border-color: #0d6efd;
  box-shadow: 0 4px 12px rgba(13, 110, 253, 0.15);
}
/* 上传按钮 */
.upload-btn {
  width: 44px;
  height: 44px;
  border: 2px dashed #dee2e6;
  border-radius: 12px;
  background: transparent;
  color: #6c757d;
  display: flex;
  align-items: center;
  justify-content: center;
  cursor: pointer;
  transition: all 0.3s ease;
}
.upload-btn:hover {
  border-color: #0d6efd;
  color: #0d6efd;
  background: rgba(13, 110, 253, 0.05);
  transform: scale(1.05);
}
.upload-btn:active {
  transform: scale(0.95);
}
/* 文件数量徽标 */
.badge-count {
  position: absolute;
  top: -6px;
  right: -6px;
  min-width: 18px;
  height: 18px;
  background: linear-gradient(135deg, #ff4757 0%, #ff6b81 100%);
  color: white;
  font-size: 10px;
  font-weight: bold;
  border-radius: 9px;
  display: flex;
  align-items: center;
  justify-content: center;
  box-shadow: 0 2px 6px rgba(255, 71, 87, 0.4);
  animation: badge-pulse 0.3s ease;
}
@keyframes badge-pulse {
  0% { transform: scale(0); }
  50% { transform: scale(1.2); }
  100% { transform: scale(1); }
}
/* 操作按钮 */
.action-btn {
  width: 36px;
  height: 36px;
  border: 1px solid #e9ecef;
  border-radius: 10px;
  background: white;
  color: #6b7280;
  display: flex;
  align-items: center;
  justify-content: center;
  cursor: pointer;
  transition: all 0.3s ease;
}
.action-btn:hover {
  border-color: #667eea;
  color: #667eea;
  transform: translateY(-2px);
  box-shadow: 0 4px 12px rgba(102, 126, 234, 0.15);
}
.action-btn.danger:hover {
  border-color: #ff4757;
  color: #ff4757;
  box-shadow: 0 4px 12px rgba(255, 71, 87, 0.15);
}
/* 聊天输入框 */
.chat-input {
  border: none;
  background: transparent;
  padding: 10px 0;
  font-size: 15px;
  color: #495057;
  outline: none;
}
.chat-input::placeholder {
  color: #adb5bd;
}
/* 发送按钮 */
.send-btn {
  width: 44px;
  height: 44px;
  border: none;
  border-radius: 12px;
  background: linear-gradient(135deg, #0d6efd 0%, #0b5ed7 100%);
  color: white;
  display: flex;
  align-items: center;
  justify-content: center;
  cursor: pointer;
  transition: all 0.3s ease;
  box-shadow: 0 2px 8px rgba(13, 110, 253, 0.3);
}
.send-btn:hover {
  transform: translateY(-2px);
  box-shadow: 0 4px 12px rgba(13, 110, 253, 0.4);
}
.send-btn:active {
  transform: translateY(0);
}
/* 图片预览卡片 */
.img-preview-card {
  background: white;
  border-radius: 8px;
  padding: 4px;
  box-shadow: 0 2px 8px rgba(0, 0, 0, 0.08);
  transition: all 0.3s ease;
}
.img-preview-card:hover {
  transform: translateY(-3px);
  box-shadow: 0 6px 16px rgba(0, 0, 0, 0.12);
}
/* 文档预览卡片 */
.doc-preview-card {
  transition: all 0.3s ease;
}
.doc-preview-card:hover {
  transform: translateX(4px);
  box-shadow: 0 4px 12px rgba(0, 0, 0, 0.1);
}
/* 文档图标 */
.doc-icon {
  width: 32px;
  height: 32px;
  display: flex;
  align-items: center;
  justify-content: center;
}
</style>
