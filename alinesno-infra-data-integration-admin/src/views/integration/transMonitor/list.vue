<template>
  <!--
    【转换监控】 功能列表
    @author paul
    @date 2024年3月10日
  -->
  <div class="app-container">

    <el-row :gutter="20">
        <el-col :span="8" class="el-col-panel">
            <el-row>
                <div class="col-sm-4 top-sum">
                    <i class="fab fa-google-drive fa-3x"></i>
                    <div class="task-num">
                    总转换任务数:{{transTotal}}
                    </div>
                </div>
            </el-row>
        </el-col>
        <el-col :span="8" class="el-span-8-tab el-col-panel">
            <el-row>
                <div class="col-sm-4 top-sum">
                    <i class="fas fa-chart-bar fa-3x"></i>
                    <div class="task-num">
                  总成功次数:{{transSuccess}}
                    </div>
                </div>
            </el-row>
        </el-col>
        <el-col :span="8" class="el-span-8-tab el-col-panel">
            <el-row>
                <div class="col-sm-4 top-sum">
                    <i class="fas fa-align-left fa-3x"></i>
                    <div class="task-num">
                      总失败次数:{{transFail}}
                    </div>
                </div>
            </el-row>
        </el-col>
    </el-row>
    <br>

    <el-form :model="queryParams" ref="queryForm" :inline="true" v-show="showSearch" label-width="68px">
        <el-form-item label="转换名称" prop="monitorTransid">
          <template #default="scope"  >
            <el-select  v-model="queryParams.monitorTransid" placeholder="转换名称" filterable style="width: 400px"   clearable size="small"  @keyup.enter.native="handleQuery">
              <el-option  v-for="item in Translist"
                          :key="item.id"
                          :label="item.name"
                          :value="item.id">
              </el-option>
            </el-select>
          </template>
        </el-form-item>
      <!--el-form-item label="任务分类" prop="categoryName">
        <el-select size="small" v-model="queryParams.categoryName" placeholder="请选择任务分类">
          <el-option  v-for="item in transTypeList"
                      :key="item.id"
                      :label="item.categoryName"
                      :value="item.categoryName"></el-option>
        </el-select>
      </el-form-item-->
      <el-form-item>
        <el-button type="primary" icon="el-icon-search" size="mini" @click="handleQuery">搜索</el-button>
        <el-button icon="el-icon-refresh" size="mini" @click="resetQuery">重置</el-button>
      </el-form-item>
    </el-form>

    <el-row :gutter="10" class="mb8">
      <el-col :span="1.5">
        <el-button
          type="danger"
          plain
           icon="Delete"
          size="mini"
          :disabled="multiple"
          @click="handleDelete"
        >删除
        </el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="warning"
          plain
          icon="el-icon-download"
          size="mini"
          @click="export2Excel"
        >导出
        </el-button>
      </el-col>
    </el-row>

    <el-table v-loading="loading" :data="TransMonitorList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center"/>
      <el-table-column label="转换名称" align="left" prop="nameLabel"/>
      <el-table-column label="转换类型" align="left" prop="typeLabel" width="120" />
      <el-table-column label="成功次数" align="left" prop="monitorSuccess"/>
      <el-table-column label="失败次数" align="left" prop="monitorFail"/>
      <el-table-column label="监控状态" align="left" prop="monitorStatusLabel"  width="80" />
      <el-table-column label="最近完成时间" align="left" prop="runStatus" width="180">
        <template #default="scope">
          <span>{{ parseTime(scope.row.runStatus)  }}</span>
        </template>
      </el-table-column>

<!--      <el-table-column label="监控状态" align="left" prop="monitorStatus">-->
<!--      <template #default="scope">-->
<!--        <span v-if="scope.row.monitorStatus == 1">运行</span>-->
<!--        <span v-if="scope.row.monitorStatus == 2">停止</span>-->
<!--      </template>-->
<!--      </el-table-column>-->
<!--      <el-table-column label="最近完成时间" :show-overflow-tooltip="true" align="left" prop="runStatus">-->
<!--        <template #default="scope">-->
<!--          <span>{{ scope.row.runStatus | formatDate }}</span>-->

<!--        </template>-->
<!--      </el-table-column>-->


<!--      <el-table-column label="最近调度时间" align="left" prop="lastExecuteTime" width="170">-->
<!--        <template #default="scope">-->
<!--          <span>{{ parseTime(scope.row.lastExecuteTime)  }}</span>-->
<!--        </template>-->
<!--      </el-table-column>-->
<!--      <el-table-column label="下次调度时间" align="left" prop="nextExecuteTime" width="170">-->
<!--        <template #default="scope">-->
<!--          <span>{{ parseTime(scope.row.nextExecuteTime)  }}</span>-->
<!--        </template>-->
<!--      </el-table-column>-->

<!--      <el-table-column label="操作" width="200" align="center" class-name="small-padding fixed-width" v-show="false">-->
<!--        <template #default="scope">-->
<!--          <el-button-->
<!--            size="mini"-->
<!--            type="text"-->
<!--            icon="Edit"-->
<!--            @click="handleUpdate(scope.row)"-->

<!--          >修改-->
<!--          </el-button>-->
<!--          <el-button-->
<!--            size="mini"-->
<!--            type="text"-->
<!--             icon="Delete"-->
<!--            @click="handleDelete(scope.row)"-->

<!--          >删除-->
<!--          </el-button>-->
<!--        </template>-->
<!--      </el-table-column>-->
    </el-table>

    <pagination
      v-show="total>0"
      :total="total"
      :page.sync="queryParams.pageNum"
      :limit.sync="queryParams.pageSize"
      @pagination="getList"
    />

    <!-- 添加或修改【转换监控】对话框 -->
    <el-dialog :title="title" v-model="open" width="800px" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="80px">
        <el-form-item label="转换名称" prop="monitorTransid">
          <el-input v-model="form.monitorTransid" placeholder="请输入转换ID"/>
        </el-form-item>
        <el-form-item label="监控成功" prop="monitorSuccess">
          <el-input v-model="form.monitorSuccess" placeholder="请输入监控成功"/>
        </el-form-item>
        <el-form-item label="监控失败" prop="monitorFail">
          <el-input v-model="form.monitorFail" placeholder="请输入监控失败"/>
        </el-form-item>
        <el-form-item label="监控状态" prop="monitorStatus">
          <el-input v-model="form.monitorStatus" placeholder="请输入监控状态"/>
        </el-form-item>
        <el-form-item label="运行状态" prop="runStatus">
          <el-input v-model="form.runStatus" placeholder="请输入运行状态"/>
        </el-form-item>
        <el-form-item label="最后执行" prop="lastExecuteTime">
          <el-date-picker clearable size="small"
                          v-model="form.lastExecuteTime"
                          type="date"
                          value-format="yyyy-MM-dd"
                          placeholder="选择最后执行">
          </el-date-picker>
        </el-form-item>
        <el-form-item label="下次执行" prop="nextExecuteTime">
          <el-date-picker clearable size="small"
                          v-model="form.nextExecuteTime"
                          type="date"
                          value-format="yyyy-MM-dd"
                          placeholder="选择下次执行">
          </el-date-picker>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="submitForm">确 定</el-button>
        <el-button @click="cancel">取 消</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script   setup name="TransMonitor">
import {
  addTransMonitor,
  delTransMonitor,
  exportTransMonitor,
  getTransMonitor,
  listTransMonitor,
  updateTransMonitor,
  getTaskCount
} from "@/api/integration/TransMonitor";
import {listTrans } from "@/api/integration/Trans";
import  Condition  from "@/api/search/condition";
import  searchParam  from "@/api/search/searchform";
import {listJob} from "@/api/integration/Job";
import {listJobMonitor} from "@/api/integration/JobMonitor";

import { ref, reactive, onMounted} from 'vue';

import { useRouter, useRoute } from 'vue-router';

const router=useRouter() ;

const route=useRoute()   ;

const { proxy } = getCurrentInstance();

// 弹出层标题
const title = ref( "");

// 总条数
const total = ref( 0);

//总转换任务数
const transTotal = ref( 0);

//总成功次数
const transSuccess = ref( 0);

//总失败次数
const transFail = ref( 0);

// 遮罩层
const loading = ref(true);

// 非单个禁用
const single = ref(true);

// 非多个禁用
const multiple = ref(true);

// 显示搜索条件
const showSearch = ref(true);

// 是否显示弹出层
const open = ref(false);

// 选中数组
const names = ref([]);

// 选中数组
const ids = ref([]);

const searchParams = ref([]);

// 【转换监控】表格数据
const TransMonitorList = ref([]);

const Translist = ref([]);

const exportTransMonitorList = ref([]);

const data = reactive({
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        monitorJobId: null,
        monitorTransid: null,
        monitorSuccess: null,
        monitorFail: null,
        monitorStatus: null,
        runStatus: null,
        lastExecuteTime: null,
        nextExecuteTime: null,
        transName:null,
        transType:null
      },

      // 查询参数配置对象
      queryParamsConfig: {
        monitorTransid: Condition.eq()
      },

      // 表单参数
      form: {},

      // 表单校验
      rules: {
        monitorTransid: [
          {required: true, message: "转换ID不能为空", trigger: "blur"}
        ],
        monitorSuccess: [
          {required: true, message: "监控成功不能为空", trigger: "blur"}
        ],
        monitorFail: [
          {required: true, message: "监控失败不能为空", trigger: "blur"}
        ],
        monitorStatus: [
          {required: true, message: "监控状态不能为空", trigger: "blur"}
        ],
        runStatus: [
          {required: true, message: "运行状态不能为空", trigger: "blur"}
        ],
        lastExecuteTime: [
          {required: true, message: "最后执行不能为空", trigger: "blur"}
        ],
        nextExecuteTime: [
          {required: true, message: "下次执行不能为空", trigger: "blur"}
        ]
      },

      exportqueryParams: {
        pageNum: 1,
        pageSize: 10,
        monitorJobId: null,
        monitorSuccess: null,
        monitorFail: null,
        monitorStatus: null,
        runStatus: null,
        lastExecuteTime: null,
        nextExecuteTime: null,
        jobType:null,
        name:null,
        type:null
      }
});

const { queryParamsConfig, queryParams, form, rules, exportqueryParams } = toRefs(data);

// 页面加载后触发
onMounted(() => {
  getTaskCountF();

  getTranslist();

  getList();
})

/** 查询【转换监控】列表 */
function  getList() {
  loading.value = true;
  searchParams.value = searchParam(queryParamsConfig.value, queryParams.value);
  listTransMonitor(searchParams.value).then(response => {
    TransMonitorList.value = response.rows;
    total.value = response.total;
    loading.value = false;
  });
}

//获取任务结果
function  getTaskCountF() {
  getTaskCount().then((res) =>{
    transTotal.value = res.data.total;
    transSuccess.value = res.data.success;
    transFail.value = res.data.fail;
  });
}

function  getTranslist(){
  listTrans(queryParams.value).then(response => {
    Translist.value = response.rows;
  });
}

// 取消按钮
function  cancel() {
  open.value = false;
  reset();
}

// 表单重置
function  reset() {
  form.value = {
    monitorTransid: null,
    monitorSuccess: null,
    monitorFail: null,
    monitorStatus: null,
    runStatus: null,
    lastExecuteTime: null,
    nextExecuteTime: null
  };
  proxy.resetForm("form");
}

/** 搜索按钮操作 */
function  handleQuery() {
  queryParams.value.pageNum = 1;
  getList();
}

/** 重置按钮操作 */
function  resetQuery() {
  proxy.resetForm("queryForm");
  handleQuery();
}

// 多选框选中数据
function  handleSelectionChange(selection) {
  ids.value = selection.map(item => item.id)
  names.value = selection.map(item => item.nameLabel + ' ' )
  single.value = selection.length !== 1
  multiple.value = !selection.length
}

/** 新增按钮操作 */
function  handleAdd() {
  reset();
  open.value = true;
  title.value = "添加【转换监控】";
}

/** 修改按钮操作 */
function  handleUpdate(row) {
  reset();
  const id = row.id || ids.value
  getTransMonitor(id).then(response => {
    form.value = response.data;
    open.value = true;
    title.value = "修改【转换监控】";
  });
}

/** 提交按钮 */
function  submitForm() {
  proxy.$refs["form"].validate(valid => {
    if (valid) {
      if (form.value.monitorTransid != null) {
        updateTransMonitor(form.value).then(response => {
          proxy.$modal.msgSuccess("修改成功");
          open.value = false;
          getList();
        });
      } else {
        addTransMonitor(form.value).then(response => {
          proxy.$modal.msgSuccess("新增成功");
          open.value = false;
          getList();
        });
      }
    }
  });
}

/** 删除按钮操作 */
function  handleDelete(row) {
  const id = row.id || ids.value;
  let nameList = row.nameLabel || names.value;
  //避免弹出窗数据太长，只显示前15条数据
  if ( nameList.length > 15 ) {
    nameList = nameList.slice(0,15);
  }


  proxy.$confirm('是否确认删除【转换监控】名称为"' + nameList + '"的数据项?', "警告", {
    confirmButtonText: "确定",
    cancelButtonText: "取消",
    type: "warning"
  }).then(function () {
    return delTransMonitor(id);
  }).then(() => {
    getList();
    proxy.$modal.msgSuccess("删除成功");
  }).catch(error => {

  })
}

/** 导出按钮操作 */
function  handleExport() {
  const queryParams = queryParams.value;
  proxy.$confirm('是否确认导出所有【转换监控】数据项?', "警告", {
    confirmButtonText: "确定",
    cancelButtonText: "取消",
    type: "warning"
  }).then(function () {
    return exportTransMonitor(queryParams);
  }).then(response => {
    download(response.msg);
  })
}

function  export2Excel() {
  loading.value = true;
  exportqueryParams.value.pageSize =total.value;
  searchParams.value = searchParam(queryParamsConfig.value, exportqueryParams.value);
  listTransMonitor(searchParams.value).then(response => {
    exportTransMonitorList.value = response.rows;
    require.ensure([], () => {
      const { export_json_to_excel } = require('@/excel/Export2Excel');
      const tHeader = ['转换名称', '转换类型', '成功次数', '失败次数', '监控状态', '最近完成时间','最近调度时间','下次调度时间'];
      // 上面设置Excel的表格第一行的标题
      const filterVal = ['nameLabel', 'typeLabel', 'monitorSuccess','monitorFail','monitorStatusLabel','runStatusLabel','lastExecuteTime','nextExecuteTime'];
      // 上面的index、phone_Num、school_Name是tableData里对象的属性
      const list = exportTransMonitorList.value;  //把data里的tableData存到list
      const data = formatJson(filterVal, list);
      export_json_to_excel(tHeader, data, '转换监控');
    })
    loading.value = false;
  }).catch(
    loading.value = false
  );

}

function  formatJson(filterVal, jsonData) {
  return jsonData.map(v => filterVal.map(j => v[j]))
}

//   },
//   filters: {
//     formatDate(runStatus) {
//       var unixTimestamp = new Date(runStatus*1 )
//       var month = unixTimestamp.toLocaleDateString()
//       var hh = unixTimestamp.getHours()
//       var mi = unixTimestamp.getMinutes()
//       var ss = unixTimestamp.getSeconds()
//       var commonTime3 = month+ ' ' +hh+':'+mi+':'+ss
//       commonTime3 =commonTime3.replace('/','-')
//       commonTime3 =commonTime3.replace('/','-')
//       return commonTime3
//     }
//   }
// };
</script>

<style scoped lang="scss">
.top-sum {
    .task-num {
        float: right;
        line-height: 60px;
        font-size: 20px;
    }
    i {
        color: #005bd4 ;
        line-height: 60px;
    }
}
.el-span-8-tab {
    width: calc(33.3333333333% - 10px);
    margin-left: 10px;
}
.el-row {
    margin-bottom: 20px;
    &:last-child {
        margin-bottom: 0;
    }
}

.el-col-panel {
    border-radius: 2px;
    background: #f7f9fa;
    padding: 20px !important ;
    padding-left: 20px !important ;
    padding-right: 20px !important ;
    border: none ;
}
</style>
