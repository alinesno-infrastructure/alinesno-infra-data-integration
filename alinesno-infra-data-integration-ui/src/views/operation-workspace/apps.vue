<template>
  <div>
    <el-row class="acp-dashboard-panel" :gutter="20">
      <el-col class="panel-col" :span="19">
        <div class="grid-content">
          <div class="panel-header">
            <div class="header-title"><i class="fa-solid fa-link"></i> 业务最新巡检</div>
          </div>
          <div class="panel-body" style="height: auto;position: relative;">
              <div class="direct-box">
                <ul>
                  <li class="box-item" v-for="item in advantages" :key="item">
                    <div class="dire-panel">
                      <div class="panel-title">
                        <i class="dire-panel-icon" :class="item.icon" :alt="item.name" />
                        {{ item.title }}
                      </div>
                      <div class="panel-describe">
                        {{ item.description }}
                      </div>
                    </div>
                  </li>
                </ul>
              </div>
          </div>
        </div>
      </el-col>
      <el-col :span="5">
        <div class="grid-content">
          <div class="panel-header">
            <div class="header-title"><i class="fa-solid fa-link"></i> 应用场景</div>
          </div>
          <div class="panel-body" style="height: auto;position: relative;">
            <div class="acp-app-list">
              <ul>
                <li class="app-items" style="width:100%" v-for="item in selectedAlarmScenarios" :key="item">
                  <div class="app-info">
                    <div class="app-item-title">
                      <i :class="item.icon" :alt="item.name" />
                        {{ item.name }}
                    </div>
                    <div class="app-item desc">{{ item.description }}</div>
                  </div>
                </li>
              </ul>
            </div>
          </div>

        </div>
      </el-col>
    </el-row>

  </div>
</template>

<script setup>

const advantages = [
  { icon: 'fa-solid fa-shield-alt', title: '集成更丰富', description: '整合多种监控和异常检测系统，提供全面的告警管理解决方案，满足不同业务需求。' },
  { icon: 'fa-solid fa-chart-line', title: '告警更精准', description: '通过智能算法和数据分析技术，实现对告警的准确识别和定位，减少误报率，提高告警的准确性。' },
  { icon: 'fa-solid fa-clipboard-list', title: '协同更便捷', description: '提供协同工作平台，支持团队成员之间的快速沟通和合作，有效提升团队处理告警的效率和协同能力。' },
  { icon: 'fa-solid fa-chart-bar', title: '闭环根因分析', description: '实现从告警生成到根因分析的全流程闭环管理，帮助快速定位和解决问题，缩短故障修复时间，提升系统稳定性。' },
  { icon: 'fa-solid fa-cogs', title: '一站式管理平台', description: '提供统一的告警管理平台，集成监控、分析、处理等功能于一体，简化操作流程，提升用户体验。' },
  { icon: 'fa-solid fa-users-cog', title: 'MTTR持续优化', description: '通过告警数据分析和优化流程，持续缩短故障响应时间，不断提升系统的稳定性和可靠性。' }
];


const selectedAlarmScenarios = [
  { icon: 'fa-solid fa-desktop', name: '应用运行告警', description: '监控和管理网络设备的告警信息，包括路由器、交换机等设备的异常状态和故障信息，确保网络正常运行。' },
  { icon: 'fa-solid fa-server', name: '服务器告警', description: '实时监测服务器的性能和运行状态，及时发现服务器负载异常、存储空间不足等问题，并进行相应处理，保障服务器稳定运行。' },
  { icon: 'fa-solid fa-database', name: '数据库告警', description: '对数据库系统进行监控和告警管理，及时发现数据库连接异常、查询性能下降等问题，保障数据的安全和可靠性。' },
];


</script>