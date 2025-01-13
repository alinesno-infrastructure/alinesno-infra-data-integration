<template>
  <!--
    【定时策略】 功能列表
    @author paul
    @version 1.0.0
  -->
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" :inline="true" v-show="showSearch" label-width="68px" @submit.native.prevent>
      <el-form-item label="定时描述" prop="quartzDescription">
        <el-input
          v-model="queryParams.quartzDescription"
          placeholder="请输入定时描述"
          clearable
          size="small"
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item>
        <el-button type="primary" icon="el-icon-search" size="mini" @click="handleQuery">搜索</el-button>
        <el-button icon="el-icon-refresh" size="mini" @click="resetQuery">重置</el-button>
      </el-form-item>
    </el-form>

    <el-row :gutter="10" class="mb8">
      <el-col :span="1.5">
        <el-button
          type="primary"
          plain
          icon="el-icon-plus"
          size="mini"
          @click="handleAdd"
        >新增
        </el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="success"
          plain
          icon="Edit"
          size="mini"
          :disabled="single"
          @click="handleUpdate"
        >修改
        </el-button>
      </el-col>
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
      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :data="QuartzList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center"/>
      <el-table-column label="定时描述" align="left" prop="quartzDescription"/>
      <el-table-column label="corn" align="left" prop="quartzCron"/>
      <el-table-column label="操作" width="200" align="center" class-name="small-padding fixed-width">
        <template #default="scope">
          <el-button
            size="mini"
            type="text"
            icon="Edit"
            @click="handleUpdate(scope.row)"
          >修改
          </el-button>
          <el-button
            size="mini"
            type="text"
             icon="Delete"
            @click="handleDelete(scope.row)"
          >删除
          </el-button>
        </template>
      </el-table-column>
    </el-table>

    <pagination
      v-show="total>0"
      :total="total"
      :page.sync="queryParams.pageNum"
      :limit.sync="queryParams.pageSize"
      @pagination="getList"
    />

    <!-- 添加或修改【定时策略】对话框 -->
    <el-dialog :title="title" v-model="open" width="800px" append-to-body>
      <el-form ref="formRef" :model="form" :rules="rules" label-width="80px">
        <el-form-item label="定时描述" prop="quartzDescription">
          <el-input v-model="form.quartzDescription" maxlength="255" show-word-limit placeholder="请输入定时描述"/>
        </el-form-item>

        <el-form-item label="表达式" prop="quartzCron">
          <el-input v-model="form.quartzCron" maxlength="255" show-word-limit  slot="reference" @click="cronPopover=true" placeholder="请输入定时策略,如0 5 4 * * ?"></el-input>

<!--          <el-popover v-model:visible="cronPopover">-->



<!--&lt;!&ndash;            <vue3Cron @change="changeCron" @close="cronPopover=false" max-height="400px" i18n="cn">    </vue3Cron>&ndash;&gt;-->



<!--&lt;!&ndash;              <VueCron  @change="changeCron" @close="cronPopover=false" i18n="cn"></VueCron>&ndash;&gt;-->
<!--&lt;!&ndash;              <el-input v-model="form.quartzCron" maxlength="255" show-word-limit  readonly="true" slot="reference" @click="cronPopover=true" placeholder="请输入定时策略"></el-input>&ndash;&gt;-->
<!--&lt;!&ndash;         &ndash;&gt;-->
<!--          </el-popover>-->
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="submitForm">确 定</el-button>
        <el-button @click="cancel">取 消</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script  setup name="Quartz">
import {listQuartz, getQuartz, delQuartz, addQuartz, updateQuartz, exportQuartz ,checkIfUsed} from "@/api/integration/Quartz";
// import {VueCron} from 'vue-cron';
import  Condition  from "@/api/search/condition";
import  searchParam  from "@/api/search/searchform";

import { ref, reactive, onMounted} from 'vue';
import { useRouter, useRoute } from 'vue-router';

import vue3Cron from 'my-cron-vue3'
import 'my-cron-vue3/lib/vue3Cron.css' // 引入样式

import { isValidCron } from 'cron-validator'

const router=useRouter() ;

const route=useRoute()   ;

const { proxy } = getCurrentInstance();

// 总条数
const total = ref( 0);

// 弹出层标题
const title = ref( "");

// const cron = ref( "");

// 【定时策略】表格数据
const QuartzList = ref([]);

// 选中数组
const names = ref([]);

// 选中数组
const ids = ref([]);

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

//cronPopover
const cronPopover = ref(true);

const searchParams = ref([]);

const data = reactive({
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
      },
      // 查询参数配置对象
      queryParamsConfig: {
        quartzDescription: Condition.like()
      },
      // 表单参数
      form: {},
      // 表单校验
      rules: {
        quartzCron: [
          { required: true, message: '请输入Cron表达式,如0 5 4 * * ?', trigger: 'blur' }
        ]

      }
});

const { queryParams, queryParamsConfig, form, rules } = toRefs(data);

// 页面加载后触发
onMounted(() => {
  getList();
})

/** 查询【定时策略】列表 */
function  getList() {
  loading.value = true;
  searchParams.value = searchParam(queryParamsConfig.value, queryParams.value);
  listQuartz(searchParams.value).then(response => {
    QuartzList.value = response.rows;
    total.value = response.total;
    loading.value = false;
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
    quartzDescription: null,
    quartzCron: null
  };
  proxy.resetForm("formRef");
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

// 改变策略
function  changeCron(val){
  form.value.quartzCron = val ;
}

// 多选框选中数据
function  handleSelectionChange(selection) {
  ids.value = selection.map(item => item.id)
  names.value = selection.map(item => item.quartzDescription)
  single.value = selection.length !== 1
  multiple.value = !selection.length
}

/** 新增按钮操作 */
function  handleAdd() {
  reset();
  open.value = true;
  title.value = "添加【定时策略】";
}

/** 修改按钮操作 */
function  handleUpdate(row) {
  reset();
  const id = row.id || ids.value
  getQuartz(id).then(response => {
    form.value = response.data;
    open.value = true;
    title.value = "修改【定时策略】";
  });
}

/** 提交按钮 */
function  submitForm() {
  const isOk = isValidCron(form.value.quartzCron, {
     // seconds可以通过seconds在选项中将标志传递为 true来启用对秒的支持(例:* * * * * *);
     // alias启用alias对月份和工作日的支持(例:* * * * mon);
     // allowBlankDay可以启用该标志以使用?符号将天或工作日标记为空白(例:* * * * ?);
     // allowSevenAsSunday可以启用该标志以支持数字 7 作为星期日(例:* * * * 7);
    seconds: true,
    alias: true,
    allowBlankDay: true,
    allowSevenAsSunday: true
  });
  if ( !isOk ) {
    proxy.$modal.msgError('请输入可用的Cron表达式,如每天4点5分0秒 0 5 4 * * ?');
    return
  }

  proxy.$refs["formRef"].validate(valid => {
    if (valid) {
      if ( form.value.id != null ) {
        updateQuartz(form.value).then(response => {
          proxy.$modal.msgSuccess("修改成功");
          open.value = false;
          getList();
        });
      } else {
        addQuartz(form.value).then(response => {
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
    let nameList = row.quartzDescription || names.value;
    //避免弹出窗数据太长，只显示前15条数据
    if ( nameList.length > 15 ) {
      nameList = nameList.slice(0,15);
    }

    checkIfUsed(id).then(res => {
    if ( res.code == 200 ) {
      proxy.$confirm('是否确认删除【定时策略】定时描述为"' + nameList + '"的数据项?', "警告", {
        confirmButtonText: "确定",
        cancelButtonText: "取消",
        type: "warning"
      }).then(function () {
        return delQuartz(id);
      }).then(() => {
        getList();
        proxy.$modal.msgSuccess("删除成功");
      }).catch(error => {

      })
    } else {
      proxy.$modal.msgError(res.msg)
    }
  }).catch(error => {

  })
}

/** 导出按钮操作 */
function  handleExport() {
  const queryParams = queryParams.value;
  proxy.$confirm('是否确认导出所有【定时策略】数据项?', "警告", {
    confirmButtonText: "确定",
    cancelButtonText: "取消",
    type: "warning"
  }).then(function () {
    return exportQuartz(queryParams);
  }).then(response => {
    download(response.msg);
  })
}


</script>
