<template>
  <nav class="navbar navbar-expand-lg sticky-top">
    <div class="container">
      <!-- Logo 和品牌 -->
      <router-link class="navbar-brand d-flex align-items-center" to="/">
        <div class="brand-icon">
          <svg xmlns="http://www.w3.org/2000/svg" width="24" height="24" fill="currentColor" viewBox="0 0 16 16">
            <path d="M8 16a6 6 0 0 0 6-6c0-1.655-1.122-2.904-2.432-4.362C10.254 4.176 8.75 2.503 8 0c-.75 2.503-2.254 4.176-3.568 5.638C3.122 7.096 2 8.345 2 10a6 6 0 0 0 6 6z"/>
            <path d="M8 16c-2.5 0-4.5-.5-6-1.5C.5 12.5 0 10.5 0 8c0-1 .5-2 1-3 .5.5 1.5 1 3 1s2.5-.5 3-1c.5 1 1 2 1 3 0 2.5-.5 4.5-2 6.5-1.5 1-3.5 1.5-6 1.5z"/>
            <circle cx="8" cy="5" r="1.5"/>
          </svg>
        </div>
        <span class="brand-text ms-2">校园生活小管家</span>
        <span class="badge ai-badge ms-2">AI</span>
      </router-link>
      
      <button class="navbar-toggler custom-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#navbarNav">
        <span class="toggler-icon"></span>
      </button>
      
      <div class="collapse navbar-collapse" id="navbarNav">
        <ul class="navbar-nav ms-auto">
          <li class="nav-item">
            <router-link class="nav-link" to="/">
              <svg xmlns="http://www.w3.org/2000/svg" width="16" height="16" fill="currentColor" viewBox="0 0 16 16" class="me-1">
                <path d="M8.707 1.5a1 1 0 0 0-1.414 0L.646 8.146a.5.5 0 0 0 .708.708L8 2.207l6.646 6.647a.5.5 0 0 0 .708-.708L8 2.207 2.354 8.854a.5.5 0 0 0 .708.708L8 8.414l5.646 5.647a.5.5 0 0 0 .708-.708L8.707 1.5z"/>
                <path d="M8 16A8 8 0 1 0 8 0a8 8 0 0 0 0 16zm7-8A7 7 0 1 1 1 8a7 7 0 0 1 14 0z"/>
              </svg>
              首页
            </router-link>
          </li>
          <li class="nav-item">
            <router-link class="nav-link" to="/schedule">
              <svg xmlns="http://www.w3.org/2000/svg" width="16" height="16" fill="currentColor" viewBox="0 0 16 16" class="me-1">
                <path d="M3.5 0a.5.5 0 0 1 .5.5V1h8V.5a.5.5 0 0 1 1 0V1h1a2 2 0 0 1 2 2v11a2 2 0 0 1-2 2H2a2 2 0 0 1-2-2V3a2 2 0 0 1 2-2h1V.5a.5.5 0 0 1 .5-.5zM1 4v10a1 1 0 0 0 1 1h12a1 1 0 0 0 1-1V4H1z"/>
              </svg>
              我的课表
            </router-link>
          </li>
          <li class="nav-item">
            <router-link class="nav-link" to="/remind">
              <svg xmlns="http://www.w3.org/2000/svg" width="16" height="16" fill="currentColor" viewBox="0 0 16 16" class="me-1">
                <path d="M8 3.5a.5.5 0 0 0-1 0V9a.5.5 0 0 0 .252.434l3.5 2a.5.5 0 0 0 .496-.868L8 8.71V3.5z"/>
                <path d="M8 16A8 8 0 1 0 8 0a8 8 0 0 0 0 16zm7-8A7 7 0 1 1 1 8a7 7 0 0 1 14 0z"/>
              </svg>
              我的提醒
            </router-link>
          </li>
        </ul>
      </div>
    </div>
  </nav>
</template>

<script>
export default {
  name: 'Navbar'
}
</script>

<style scoped>
.navbar {
  background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
  box-shadow: 0 4px 20px rgba(102, 126, 234, 0.3);
  padding: 12px 0;
  transition: all 0.3s ease;
}

.navbar-brand {
  font-weight: 600;
  font-size: 1.25rem;
  color: white !important;
  text-decoration: none;
  display: flex;
  align-items: center;
}

.brand-icon {
  width: 40px;
  height: 40px;
  background: rgba(255, 255, 255, 0.2);
  border-radius: 12px;
  display: flex;
  align-items: center;
  justify-content: center;
  backdrop-filter: blur(10px);
}

.brand-text {
  font-weight: 700;
  letter-spacing: -0.5px;
}

.ai-badge {
  background: linear-gradient(135deg, #00d9ff 0%, #00ff88 100%);
  color: #1a1a2e;
  font-size: 10px;
  font-weight: 700;
  padding: 3px 8px;
  border-radius: 6px;
  letter-spacing: 1px;
}

.nav-link {
  color: rgba(255, 255, 255, 0.85) !important;
  font-size: 15px;
  padding: 8px 16px !important;
  border-radius: 8px;
  transition: all 0.3s ease;
  display: flex;
  align-items: center;
}

.nav-link:hover {
  color: white !important;
  background: rgba(255, 255, 255, 0.15);
  transform: translateY(-2px);
}

.nav-link.router-link-active {
  color: white !important;
  background: rgba(255, 255, 255, 0.2);
}

/* 自定义汉堡菜单按钮 */
.custom-toggler {
  border: none;
  padding: 8px;
  background: rgba(255, 255, 255, 0.1);
  border-radius: 8px;
}

.custom-toggler:focus {
  box-shadow: none;
}

.toggler-icon {
  display: block;
  width: 22px;
  height: 2px;
  background: white;
  position: relative;
  border-radius: 2px;
}

.toggler-icon::before,
.toggler-icon::after {
  content: '';
  position: absolute;
  width: 22px;
  height: 2px;
  background: white;
  border-radius: 2px;
  left: 0;
}

.toggler-icon::before {
  top: -6px;
}

.toggler-icon::after {
  top: 6px;
}
</style>
