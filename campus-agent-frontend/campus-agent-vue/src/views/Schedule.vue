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
                <path d="M3.5 0a.5.5 0 0 1 .5.5V1h8V.5a.5.5 0 0 1 1 0V1h1a2 2 0 0 1 2 2v11a2 2 0 0 1-2 2H2a2 2 0 0 1-2-2V3a2 2 0 0 1 2-2h1V.5a.5.5 0 0 1 .5-.5zM1 4v10a1 1 0 0 0 1 1h12a1 1 0 0 0 1-1V4H1z"/>
              </svg>
            </div>
            <h2 class="header-title">我的课表</h2>
            <p class="header-subtitle">{{ getWeekday() }} · {{ getDateString() }}</p>
          </div>

          <!-- 星期选择器 -->
          <div class="weekday-tabs mb-3">
            <button
              v-for="(day, idx) in weekdays"
              :key="idx"
              :class="['weekday-tab', { active: currentWeekday === idx + 1 }]"
              @click="switchWeekday(idx + 1)"
            >
              {{ day }}
            </button>
          </div>
          
          <!-- 课表卡片 -->
          <div class="content-card">
            <div class="card-header-custom">
              <div class="d-flex align-items-center">
                <svg xmlns="http://www.w3.org/2000/svg" width="20" height="20" fill="currentColor" viewBox="0 0 16 16" class="me-2">
                  <path d="M11.742 10.344a6.5 6.5 0 1 0-1.397 1.398h-.001c.03.04.062.078.098.115l3.85 3.85a1 1 0 0 0 1.415-1.414l-3.85-3.85a1.007 1.007 0 0 0-.115-.1zM12 6.5a5.5 5.5 0 1 1-11 0 5.5 5.5 0 0 1 11 0z"/>
                </svg>
                {{ weekdays[currentWeekday - 1] }}课程
              </div>
              <div class="d-flex align-items-center gap-2">
                <span class="count-badge">{{ scheduleList.length }} 节课</span>
                <button class="add-btn" @click="showAddModal = true">
                  <svg xmlns="http://www.w3.org/2000/svg" width="16" height="16" fill="currentColor" viewBox="0 0 16 16">
                    <path d="M8 4a.5.5 0 0 1 .5.5v3h3a.5.5 0 0 1 0 1h-3v3a.5.5 0 0 1-1 0v-3h-3a.5.5 0 0 1 0-1h3v-3A.5.5 0 0 1 8 4z"/>
                  </svg>
                  添加课程
                </button>
              </div>
            </div>
            <div class="card-body-custom">
              <!-- 课程列表 -->
              <div v-if="scheduleList.length > 0" class="course-list">
                <div v-for="(item, index) in scheduleList" :key="item.id" class="course-item">
                  <div class="course-time">
                    <div class="time-dot" :style="{ background: getCourseColor(index) }"></div>
                    <div class="time-text">{{ item.time }}</div>
                  </div>
                  <div class="course-card" :style="{ borderLeftColor: getCourseColor(index) }">
                    <div class="course-name">{{ item.courseName }}</div>
                    <div class="course-info">
                      <span class="info-item">
                        <svg xmlns="http://www.w3.org/2000/svg" width="14" height="14" fill="currentColor" viewBox="0 0 16 16">
                          <path d="M8 16s6-5.686 6-10A6 6 0 0 0 2 6c0 4.314 6 10 6 10zm0-7a3 3 0 1 1 0-6 3 3 0 0 1 0 6z"/>
                        </svg>
                        {{ item.place }}
                      </span>
                    </div>
                    <div class="course-actions">
                      <button class="action-btn edit" @click="editCourse(item)" title="编辑">
                        <svg xmlns="http://www.w3.org/2000/svg" width="14" height="14" fill="currentColor" viewBox="0 0 16 16">
                          <path d="M12.146.146a.5.5 0 0 1 .708 0l3 3a.5.5 0 0 1 0 .708l-10 10a.5.5 0 0 1-.168.11l-5 2a.5.5 0 0 1-.65-.65l2-5a.5.5 0 0 1 .11-.168l10-10zM11.207 2.5 13.5 4.793 14.793 3.5 12.5 1.207 11.207 2.5zm1.586 3L10.5 3.207 4 9.707V10h.5a.5.5 0 0 1 .5.5v.5h.5a.5.5 0 0 1 .5.5v.5h.293l6.5-6.5zm-9.761 5.175-.106.106-1.528 3.821 3.821-1.528.106-.106A.5.5 0 0 1 5 12.5V12h-.5a.5.5 0 0 1-.5-.5V11h-.5a.5.5 0 0 1-.468-.325z"/>
                        </svg>
                      </button>
                      <button class="action-btn delete" @click="deleteCourseConfirm(item)" title="删除">
                        <svg xmlns="http://www.w3.org/2000/svg" width="14" height="14" fill="currentColor" viewBox="0 0 16 16">
                          <path d="M5.5 5.5A.5.5 0 0 1 6 6v6a.5.5 0 0 1-1 0V6a.5.5 0 0 1 .5-.5zm2.5 0a.5.5 0 0 1 .5.5v6a.5.5 0 0 1-1 0V6a.5.5 0 0 1 .5-.5zm3 .5a.5.5 0 0 0-1 0v6a.5.5 0 0 0 1 0V6z"/>
                          <path fill-rule="evenodd" d="M14.5 3a1 1 0 0 1-1 1H13v9a2 2 0 0 1-2 2H5a2 2 0 0 1-2-2V4h-.5a1 1 0 0 1-1-1V2a1 1 0 0 1 1-1H6a1 1 0 0 1 1-1h2a1 1 0 0 1 1 1h3.5a1 1 0 0 1 1 1v1zM4.118 4 4 4.059V13a1 1 0 0 0 1 1h6a1 1 0 0 0 1-1V4.059L11.882 4H4.118zM2.5 3V2h11v1h-11z"/>
                        </svg>
                      </button>
                    </div>
                  </div>
                </div>
              </div>
              
              <!-- 空状态 -->
              <div v-else class="empty-state">
                <div class="empty-icon">
                  <svg xmlns="http://www.w3.org/2000/svg" width="48" height="48" fill="currentColor" viewBox="0 0 16 16">
                    <path d="M3.5 0a.5.5 0 0 1 .5.5V1h8V.5a.5.5 0 0 1 1 0V1h1a2 2 0 0 1 2 2v11a2 2 0 0 1-2 2H2a2 2 0 0 1-2-2V3a2 2 0 0 1 2-2h1V.5a.5.5 0 0 1 .5-.5zM1 4v10a1 1 0 0 0 1 1h12a1 1 0 0 0 1-1V4H1z"/>
                  </svg>
                </div>
                <div class="empty-text">今日无课</div>
                <div class="empty-hint">点击右上角「添加课程」开始安排你的课程表 💪</div>
              </div>
            </div>
          </div>
        </div>
      </div>
    </div>

    <!-- 添加/编辑课程弹窗 -->
    <div v-if="showAddModal" class="modal-overlay" @click.self="closeModal">
      <div class="modal-content">
        <div class="modal-header">
          <h3>{{ editingCourse ? '编辑课程' : '添加课程' }}</h3>
          <button class="close-btn" @click="closeModal">
            <svg xmlns="http://www.w3.org/2000/svg" width="20" height="20" fill="currentColor" viewBox="0 0 16 16">
              <path d="M4.646 4.646a.5.5 0 0 1 .708 0L8 7.293l2.646-2.647a.5.5 0 0 1 .708.708L8.707 8l2.647 2.646a.5.5 0 0 1-.708.708L8 8.707l-2.646 2.647a.5.5 0 0 1-.708-.708L7.293 8 4.646 5.354a.5.5 0 0 1 0-.708z"/>
            </svg>
          </button>
        </div>
        <div class="modal-body">
          <div class="form-group">
            <label>课程名称 *</label>
            <input v-model="courseForm.courseName" type="text" placeholder="例如：高等数学" />
          </div>
          <div class="form-group">
            <label>上课时间 *</label>
            <input v-model="courseForm.time" type="text" placeholder="例如：08:00-09:40" />
          </div>
          <div class="form-group">
            <label>上课地点 *</label>
            <input v-model="courseForm.place" type="text" placeholder="例如：教学楼A101" />
          </div>
          <div class="form-group">
            <label>星期 *</label>
            <select v-model="courseForm.weekday">
              <option v-for="(day, idx) in weekdays" :key="idx" :value="idx + 1">{{ day }}</option>
            </select>
          </div>
        </div>
        <div class="modal-footer">
          <button class="btn-cancel" @click="closeModal">取消</button>
          <button class="btn-confirm" @click="saveCourse">{{ editingCourse ? '保存修改' : '添加课程' }}</button>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
export default {
  name: 'Schedule',
  data() {
    return {
      scheduleList: [],
      userId: this.$cookies.get('userId') || 1,
      weekdays: ['周一', '周二', '周三', '周四', '周五', '周六', '周日'],
      currentWeekday: new Date().getDay() === 0 ? 7 : new Date().getDay(),
      showAddModal: false,
      editingCourse: null,
      courseForm: {
        courseName: '',
        time: '',
        place: '',
        weekday: 1
      }
    }
  },
  mounted() {
    this.getSchedule()
  },
  methods: {
    async getSchedule() {
      try {
        const res = await this.$axios.get('/schedule/weekday', {
          params: { userId: this.userId, weekday: this.currentWeekday }
        })
        if (res.data.code === 200) {
          this.scheduleList = res.data.data
        }
      } catch (err) {
        console.error(err)
      }
    },
    switchWeekday(weekday) {
      this.currentWeekday = weekday
      this.getSchedule()
    },
    getWeekday() {
      const weekday = ['周日', '周一', '周二', '周三', '周四', '周五', '周六']
      return weekday[new Date().getDay()]
    },
    getDateString() {
      const today = new Date()
      return `${today.getMonth() + 1}月${today.getDate()}日`
    },
    getCourseColor(index) {
      const colors = [
        '#667eea', '#f093fb', '#4facfe', '#00f2fe',
        '#43e97b', '#38f9d7', '#fa709a', '#fee140'
      ]
      return colors[index % colors.length]
    },
    editCourse(course) {
      this.editingCourse = course
      this.courseForm = {
        id: course.id,
        courseName: course.courseName,
        time: course.time,
        place: course.place,
        weekday: course.weekday
      }
      this.showAddModal = true
    },
    closeModal() {
      this.showAddModal = false
      this.editingCourse = null
      this.courseForm = {
        courseName: '',
        time: '',
        place: '',
        weekday: this.currentWeekday
      }
    },
    async saveCourse() {
      if (!this.courseForm.courseName || !this.courseForm.time || !this.courseForm.place) {
        alert('请填写完整的课程信息')
        return
      }
      try {
        const data = {
          ...this.courseForm,
          userId: this.userId
        }
        let res
        if (this.editingCourse) {
          res = await this.$axios.post('/schedule/update', data)
        } else {
          res = await this.$axios.post('/schedule/add', data)
        }
        if (res.data.code === 200) {
          this.closeModal()
          this.getSchedule()
        } else {
          alert(res.data.msg || '操作失败')
        }
      } catch (err) {
        console.error(err)
        alert('操作失败，请重试')
      }
    },
    deleteCourseConfirm(course) {
      if (!confirm(`确定要删除「${course.courseName}」吗？`)) return
      this.doDeleteCourse(course.id)
    },
    async doDeleteCourse(id) {
      try {
        const res = await this.$axios.post('/schedule/delete', null, {
          params: { id }
        })
        if (res.data.code === 200) {
          this.getSchedule()
        } else {
          alert(res.data.msg || '删除失败')
        }
      } catch (err) {
        console.error(err)
        alert('删除失败，请重试')
      }
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
  background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
  top: -80px;
  right: -80px;
}

.circle-2 {
  width: 300px;
  height: 300px;
  background: linear-gradient(135deg, #4facfe 0%, #00f2fe 100%);
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

/* 页面标题 */
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

/* 星期选择器 */
.weekday-tabs {
  display: flex;
  gap: 8px;
  justify-content: center;
  animation: slideUp 0.6s ease-out 0.05s both;
}

.weekday-tab {
  padding: 8px 16px;
  border: 1px solid #e9ecef;
  border-radius: 20px;
  background: white;
  color: #6b7280;
  font-size: 13px;
  font-weight: 500;
  cursor: pointer;
  transition: all 0.3s ease;
}

.weekday-tab:hover {
  border-color: #4facfe;
  color: #4facfe;
}

.weekday-tab.active {
  background: linear-gradient(135deg, #4facfe 0%, #00f2fe 100%);
  color: white;
  border-color: transparent;
  box-shadow: 0 4px 12px rgba(79, 172, 254, 0.3);
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
  background: linear-gradient(135deg, #4facfe 0%, #00f2fe 100%);
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

.add-btn {
  display: inline-flex;
  align-items: center;
  gap: 4px;
  padding: 6px 14px;
  background: rgba(255, 255, 255, 0.2);
  border: 1px solid rgba(255, 255, 255, 0.3);
  border-radius: 20px;
  color: white;
  font-size: 13px;
  font-weight: 500;
  cursor: pointer;
  transition: all 0.3s ease;
}

.add-btn:hover {
  background: white;
  color: #4facfe;
  border-color: white;
}

.card-body-custom {
  padding: 20px;
}

/* 课程列表 */
.course-list {
  display: flex;
  flex-direction: column;
  gap: 16px;
}

.course-item {
  display: flex;
  gap: 16px;
}

.course-time {
  display: flex;
  flex-direction: column;
  align-items: center;
  width: 80px;
  flex-shrink: 0;
}

.time-dot {
  width: 12px;
  height: 12px;
  border-radius: 50%;
  margin-bottom: 8px;
  box-shadow: 0 2px 8px currentColor;
}

.time-text {
  font-size: 13px;
  color: #6b7280;
  font-weight: 500;
}

.course-card {
  flex: 1;
  background: #f8f9fa;
  border-radius: 12px;
  padding: 16px 20px;
  border-left: 4px solid;
  transition: all 0.3s ease;
  position: relative;
}

.course-card:hover {
  transform: translateX(4px);
  box-shadow: 0 4px 12px rgba(0, 0, 0, 0.08);
}

.course-name {
  font-size: 16px;
  font-weight: 600;
  color: #1a1a2e;
  margin-bottom: 8px;
}

.course-info {
  display: flex;
  gap: 16px;
}

.info-item {
  display: flex;
  align-items: center;
  gap: 4px;
  font-size: 13px;
  color: #6b7280;
}

.course-actions {
  position: absolute;
  top: 12px;
  right: 12px;
  display: flex;
  gap: 6px;
  opacity: 0;
  transition: opacity 0.3s ease;
}

.course-card:hover .course-actions {
  opacity: 1;
}

.course-actions .action-btn {
  width: 28px;
  height: 28px;
  border-radius: 6px;
  border: none;
  display: flex;
  align-items: center;
  justify-content: center;
  cursor: pointer;
  transition: all 0.3s ease;
}

.course-actions .action-btn.edit {
  background: #e3f2fd;
  color: #1976d2;
}

.course-actions .action-btn.edit:hover {
  background: #1976d2;
  color: white;
}

.course-actions .action-btn.delete {
  background: #ffebee;
  color: #d32f2f;
}

.course-actions .action-btn.delete:hover {
  background: #d32f2f;
  color: white;
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
  border-radius: 20px;
  width: 90%;
  max-width: 420px;
  box-shadow: 0 20px 60px rgba(0, 0, 0, 0.2);
  animation: slideUp 0.3s ease;
}

.modal-header {
  display: flex;
  align-items: center;
  justify-content: space-between;
  padding: 20px 24px;
  border-bottom: 1px solid #e9ecef;
}

.modal-header h3 {
  font-size: 18px;
  font-weight: 600;
  color: #1a1a2e;
  margin: 0;
}

.close-btn {
  width: 32px;
  height: 32px;
  border-radius: 50%;
  border: none;
  background: #f8f9fa;
  color: #6b7280;
  display: flex;
  align-items: center;
  justify-content: center;
  cursor: pointer;
  transition: all 0.3s ease;
}

.close-btn:hover {
  background: #e9ecef;
  color: #1a1a2e;
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
  font-size: 14px;
  font-weight: 500;
  color: #495057;
  margin-bottom: 8px;
}

.form-group input,
.form-group select {
  width: 100%;
  padding: 12px 16px;
  border: 1px solid #e9ecef;
  border-radius: 10px;
  font-size: 14px;
  color: #1a1a2e;
  transition: all 0.3s ease;
  box-sizing: border-box;
}

.form-group input:focus,
.form-group select:focus {
  outline: none;
  border-color: #4facfe;
  box-shadow: 0 0 0 3px rgba(79, 172, 254, 0.1);
}

.form-group input::placeholder {
  color: #adb5bd;
}

.modal-footer {
  display: flex;
  gap: 12px;
  padding: 20px 24px;
  border-top: 1px solid #e9ecef;
}

.btn-cancel,
.btn-confirm {
  flex: 1;
  padding: 12px 20px;
  border-radius: 10px;
  font-size: 14px;
  font-weight: 500;
  cursor: pointer;
  transition: all 0.3s ease;
}

.btn-cancel {
  background: #f8f9fa;
  border: 1px solid #e9ecef;
  color: #6b7280;
}

.btn-cancel:hover {
  background: #e9ecef;
  color: #1a1a2e;
}

.btn-confirm {
  background: linear-gradient(135deg, #4facfe 0%, #00f2fe 100%);
  border: none;
  color: white;
  box-shadow: 0 4px 12px rgba(79, 172, 254, 0.3);
}

.btn-confirm:hover {
  transform: translateY(-2px);
  box-shadow: 0 6px 16px rgba(79, 172, 254, 0.4);
}
</style>
