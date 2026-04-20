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
                <path d="M8 15A7 7 0 1 1 8 1a7 7 0 0 1 0 14zm0 1A8 8 0 1 0 8 0a8 8 0 0 0 0 16z"/>
                <path d="M4 9.5a.5.5 0 0 1 .5-.5h7a.5.5 0 0 1 0 1h-7a.5.5 0 0 1-.5-.5zm0 3a.5.5 0 0 1 .5-.5h7a.5.5 0 0 1 0 1h-7a.5.5 0 0 1-.5-.5z"/>
              </svg>
            </div>
            <h2 class="header-title">食堂推荐</h2>
            <p class="header-subtitle">探索校园美食，发现美味</p>
          </div>

          <!-- 食堂列表 -->
          <div class="dining-list">
            <div v-for="(item, index) in diningList" :key="index" class="dining-card">
              <div class="dining-header">
                <div class="dining-icon" :style="{ background: getIconBg(index) }">
                  {{ item.emoji }}
                </div>
                <div class="dining-info">
                  <h3>{{ item.name }}</h3>
                  <p>{{ item.location }}</p>
                </div>
                <div class="dining-rating">
                  <span class="rating-score">{{ item.rating }}</span>
                  <span class="rating-text">分</span>
                </div>
              </div>
              <div class="dining-tags">
                <span v-for="(tag, i) in item.tags" :key="i" class="tag">{{ tag }}</span>
              </div>
              <div class="dining-highlight">
                <svg xmlns="http://www.w3.org/2000/svg" width="16" height="16" fill="currentColor" viewBox="0 0 16 16">
                  <path d="M8 16s6-5.686 6-10A6 6 0 0 0 2 6c0 4.314 6 10 6 10z"/>
                </svg>
                <span>{{ item.highlight }}</span>
              </div>
            </div>
          </div>

          <!-- 今日推荐 -->
          <div class="today-recommend">
            <div class="recommend-header">
              <svg xmlns="http://www.w3.org/2000/svg" width="20" height="20" fill="currentColor" viewBox="0 0 16 16">
                <path d="M8 16A8 8 0 1 0 8 0a8 8 0 0 0 0 16zm.93-9.412-1 4.705c-.07.34.029.533.304.533.194 0 .487-.07.686-.246l-.088.416c-.287.346-.92.598-1.465.598-.703 0-1.002-.422-.808-1.319l.738-3.468c.064-.293.006-.399-.287-.47l-.451-.081.082-.381 2.29-.287zM8 5.5a1 1 0 1 1 0-2 1 1 0 0 1 0 2z"/>
              </svg>
              <span>小管家今日推荐</span>
            </div>
            <div class="recommend-content">
              <div class="recommend-dish">{{ todayRecommend.dish }}</div>
              <div class="recommend-place">{{ todayRecommend.place }}</div>
            </div>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
export default {
  name: 'Dining',
  data() {
    return {
      diningList: [
        {
          name: '第一食堂',
          emoji: '🍜',
          location: '主教学楼东侧',
          rating: '4.8',
          tags: ['快餐', '面食', '早餐'],
          highlight: '早餐种类丰富，面食口感绝佳'
        },
        {
          name: '第二食堂',
          emoji: '🍛',
          location: '图书馆对面',
          rating: '4.6',
          tags: ['套餐', '炒菜', '经济实惠'],
          highlight: '性价比之王，学生党首选'
        },
        {
          name: '第三食堂',
          emoji: '🍲',
          location: '体育馆旁',
          rating: '4.7',
          tags: ['麻辣香锅', '火锅', '夜宵'],
          highlight: '麻辣系列yyds，还有夜宵供应'
        },
        {
          name: '清真食堂',
          emoji: '🥙',
          location: '东门附近',
          rating: '4.5',
          tags: ['清真', '牛羊肉', '面食'],
          highlight: '正宗清真风味，羊肉串必点'
        }
      ],
      todayRecommend: {
        dish: '麻辣香锅+米饭套餐',
        place: '第三食堂二楼 ☝️'
      }
    }
  },
  methods: {
    getIconBg(index) {
      const colors = [
        'linear-gradient(135deg, #ff6b6b 0%, #ee5a24 100%)',
        'linear-gradient(135deg, #f9ca24 0%, #f0932b 100%)',
        'linear-gradient(135deg, #eb4d4b 0%, #b33939 100%)',
        'linear-gradient(135deg, #6ab04c 0%, #38a169 100%)'
      ]
      return colors[index % colors.length]
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
  background: linear-gradient(135deg, #ff6b6b 0%, #ee5a24 100%);
  top: -80px;
  right: -80px;
}

.circle-2 {
  width: 300px;
  height: 300px;
  background: linear-gradient(135deg, #f9ca24 0%, #f0932b 100%);
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
  background: linear-gradient(135deg, #ff6b6b 0%, #ee5a24 100%);
  border-radius: 18px;
  display: flex;
  align-items: center;
  justify-content: center;
  color: white;
  margin: 0 auto 16px;
  box-shadow: 0 8px 24px rgba(238, 90, 36, 0.3);
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

/* 食堂卡片列表 */
.dining-list {
  display: flex;
  flex-direction: column;
  gap: 16px;
  animation: slideUp 0.6s ease-out 0.1s both;
}

.dining-card {
  background: white;
  border-radius: 16px;
  padding: 20px;
  box-shadow: 0 4px 20px rgba(0, 0, 0, 0.06);
  transition: all 0.3s ease;
}

.dining-card:hover {
  transform: translateY(-4px);
  box-shadow: 0 8px 30px rgba(0, 0, 0, 0.1);
}

.dining-header {
  display: flex;
  align-items: center;
  gap: 16px;
  margin-bottom: 12px;
}

.dining-icon {
  width: 56px;
  height: 56px;
  border-radius: 14px;
  display: flex;
  align-items: center;
  justify-content: center;
  font-size: 28px;
  box-shadow: 0 4px 12px rgba(0, 0, 0, 0.1);
}

.dining-info {
  flex: 1;
}

.dining-info h3 {
  font-size: 18px;
  font-weight: 600;
  color: #1a1a2e;
  margin-bottom: 4px;
}

.dining-info p {
  font-size: 13px;
  color: #6b7280;
  margin: 0;
}

.dining-rating {
  display: flex;
  align-items: baseline;
  gap: 2px;
}

.rating-score {
  font-size: 24px;
  font-weight: 700;
  color: #ff6b6b;
}

.rating-text {
  font-size: 12px;
  color: #6b7280;
}

.dining-tags {
  display: flex;
  gap: 8px;
  margin-bottom: 12px;
}

.tag {
  padding: 4px 12px;
  background: #f8f9fa;
  border-radius: 20px;
  font-size: 12px;
  color: #6b7280;
}

.dining-highlight {
  display: flex;
  align-items: center;
  gap: 8px;
  padding: 12px 16px;
  background: linear-gradient(135deg, #fff5f5 0%, #fff 100%);
  border-radius: 10px;
  color: #ff6b6b;
  font-size: 14px;
}

.dining-highlight svg {
  flex-shrink: 0;
}

/* 今日推荐 */
.today-recommend {
  margin-top: 24px;
  background: linear-gradient(135deg, #ff6b6b 0%, #ee5a24 100%);
  border-radius: 16px;
  padding: 24px;
  color: white;
  animation: slideUp 0.6s ease-out 0.2s both;
}

.recommend-header {
  display: flex;
  align-items: center;
  gap: 8px;
  font-size: 14px;
  opacity: 0.9;
  margin-bottom: 12px;
}

.recommend-content {
  text-align: center;
}

.recommend-dish {
  font-size: 22px;
  font-weight: 700;
  margin-bottom: 8px;
}

.recommend-place {
  font-size: 14px;
  opacity: 0.9;
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
</style>
