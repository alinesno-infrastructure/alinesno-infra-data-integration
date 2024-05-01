<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" :inline="true" v-show="showSearch" label-width="68px" onsubmit="return false;">
      <el-form-item label="仓库名称" prop="condition[gitName|like]">
        <el-input
          v-model="queryParams['condition[gitName|like]']"
          placeholder="请输入仓库名称"
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

<!--    <el-row :gutter="10" class="mb8">-->
<!--      <el-col :span="1.5">-->
<!--        <el-button-->
<!--          type="primary"-->
<!--          plain-->
<!--          icon="el-icon-plus"-->
<!--          size="mini"-->
<!--          @click="handleAdd"-->
<!--        >新增</el-button>-->
<!--      </el-col>-->
<!--      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>-->
<!--    </el-row>-->

    <el-table v-loading="loading" :data="typeList" @selection-change="handleSelectionChange" align="left" >
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column label="图标" width="80" align="center" prop="gitName">
        <template #default="scope">
          <i class="git-icon fab fa-gitkraken" />
        </template>
      </el-table-column>
      <el-table-column label="仓库名称" align="left"  prop="gitName"  :show-overflow-tooltip="true" />
      <el-table-column label="简介" align="left" prop="fieldProp"   :show-overflow-tooltip="true" />
      <el-table-column label="仓库地址" align="left" prop="gitUrl"  :show-overflow-tooltip="true" />
      <el-table-column label="分支" align="left" prop="branchName" width="100"  :show-overflow-tooltip="true" />
      <el-table-column label="账号" align="left" prop="gitUserName"   width="180" :show-overflow-tooltip="true" />
      <el-table-column label="操作" width="160" align="center" class-name="small-padding fixed-width">
        <template  #default="scope">
          <el-button
              size="mini"
              type="text"
              icon="Edit"
              @click="handleUpdate(scope.row)"
          >修改</el-button>
          <el-button
              size="mini"
              type="text"
              icon="Delete"
              @click="handleDelete(scope.row)"
          >删除</el-button>
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

    <!-- 添加或修改仓库类型对话框 -->
    <el-dialog :title="title" :rules="rules" v-model="open" width="800px" append-to-body>
      <el-form ref="formRef" :model="form" :rules="rules" label-width="80px">

          <!--el-form-item label="仓库类型" prop="gitType">
            <el-radio-group v-model="form.gitType">
              <el-radio :label="item.value" v-for="(item , index) in svnList" :key="index" :selected="item.status == 1">
                   {{ item.name }}
              </el-radio>
            </el-radio-group>
          </el-form-item-->

          <el-form-item label="仓库名称" prop="gitName">
            <el-input v-model="form.gitName" maxlength="64" show-word-limit placeholder="例如:企业代码管理平台" />
          </el-form-item>
          <el-form-item label="仓库简介" prop="fieldProp">
            <el-input v-model="form.fieldProp" maxlength="128" show-word-limit placeholder="例如:企业搭建的内部代码仓库" />
          </el-form-item>
          <el-form-item label="仓库地址" prop="gitUrl">
            <el-input v-model="form.gitUrl" maxlength="255" show-word-limit placeholder="请输入仓库地址,例如:https://gitlab.your-domain.com/namespace/projectName.git" />
          </el-form-item>
          <el-form-item label="分支" prop="branchName">
            <el-input v-model="form.branchName" maxlength="255" show-word-limit placeholder="请输入仓库分支,例如:master" />
          </el-form-item>
          <el-form-item  label="用户名" prop="gitUserName">
            <el-input v-model="form.gitUserName" maxlength="64" show-word-limit placeholder="请输入用户名,例如:administrator" />
          </el-form-item>
          <el-form-item label="密码" prop="password">
            <el-input v-model="form.password" maxlength="20" show-word-limit placeholder="请输入仓库管理员密码" show-password/>
          </el-form-item>

        </el-form>

        <!--el-link href="https://gitlab.your-domain.com" style="margin-left:50px" type="primary" target="_blank">PersonToken获取方式</el-link-->

      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="submitFormGitlab">连接仓库测试 </el-button>
        <el-button type="primary" @click="submitForm">确 定</el-button>
        <el-button @click="cancel">取 消</el-button>
      </div>
    </el-dialog>

    <!-- 绑定github/gitee -->
    <el-dialog
      title="提示"
      v-model="githubDialogVisible"
      width="30%">
      <el-timeline>
        <el-timeline-item
          @click="openGithubBing(activity)"
          v-for="(activity, index) in activities"
          :key="index">
          <a v-if="activity.type === 'openLink'" :href="githubBingLink" target="_blank"> {{activity.content}} </a>
          <a v-if="activity.type === 'closeBack'" href="javascript:void(0)" @click="closeGithub"> {{activity.content}} </a>
        </el-timeline-item>
      </el-timeline>
      <span slot="footer" class="dialog-footer">
        <el-button type="primary" @click="closeGithub">确 定</el-button>
      </span>
    </el-dialog>

    <!-- 绑定gitlab -->
    <el-dialog
      title="绑定基线"
      v-model="gitlabDialogVisible"
      width="800px">

      <el-form ref="formGitlab" :model="formGitlab" :rules="rules" label-width="80px">
          <el-form-item label="仓库地址" prop="gitUrl">
            <el-input v-model="formGitlab.gitUrl" placeholder="请输入仓库地址,例如:https://gitlab.your-domain.com/namespace/projectName.git" />
          </el-form-item>
          <el-form-item label="分支" prop="branchName">
            <el-input v-model="formGitlab.branchName" placeholder="请输入仓库分支,例如:master" />
          </el-form-item>
          <el-form-item v-if="currentBingGit === 'gitlab'" label="用户名" prop="userName">
            <el-input v-model="formGitlab.userName" placeholder="请输入用户名,例如:administrator" />
          </el-form-item>
          <el-form-item label="密码" prop="password">
            <el-input v-model="formGitlab.password" placeholder="请输入仓库管理员密码" show-password/>
          </el-form-item>
        </el-form>

        <!--el-link-- v-if="currentBingGit === 'gitlab'" href="https://help.aliyun.com/document_detail/202197.htm?spm=5176.8351553.help.dexternal.7f231991B2jC51#section-h93-qn7-zz2" style="margin-left:50px" type="primary" target="_blank">PersonToken获取方式</el-link-->
        <!--el-link-- v-if="currentBingGit === 'gitea'" href="javascript:void(0)" style="margin-left:50px" type="primary" target="_blank">PersonToken获取方式</--el-link-->

      <span slot="footer" class="dialog-footer">
        <el-button type="primary" @click="submitFormGitlab">提 交</el-button>
      </span>
    </el-dialog>

  </div>
</template>

<script  setup name="buildGit">

import { listBuildGit,
  getBuildGit,
  delBuildGit,
  addBuildGit,
  updateBuildGit,
  exportBuildGit,
  bingFormGitlab,
  unBing,
  getGithubAuthurl,
  checkIfUsed } from "@/api/integration/buildGit";

import { ref, reactive, onMounted} from 'vue';
import { useRouter, useRoute } from 'vue-router';

const router=useRouter() ;

const route=useRoute()   ;

const { proxy } = getCurrentInstance();


// 绑定github链接
const githubBingLink = ref("");

// 总条数
const total  = ref(0);

// 弹出层标题
const title = ref("");
// 是否显示弹出层

// 当前绑定的gitType
const currentBingGit = ref("");


 // 是否显示github会话
const githubDialogVisible = ref(false);

// 显示gitlab会话
const gitlabDialogVisible = ref(false);

// 遮罩层
const loading = ref(true);

// 导出遮罩层
const exportLoading = ref(false);

// 非单个禁用
const single = ref(true);

// 非多个禁用
const multiple = ref(true);

// 显示搜索条件
const showSearch = ref(true);

const open = ref(false);

// 选中数组
const ids = ref([]);

const gitNames = ref([]);

// 仓库类型表格数据
const typeList = ref([]);

const data = reactive({
      svnList:[
        {icon:'fab fa-gitlab' , value:'gitlab', name:'Gitlab' , isConnect:true , status:1 },
      ],
      activities: [{
          content: '点击前往源代码仓库登录',
          type:'openLink'
        }, {
          content: '返回控制台确认绑定完成',
          type:'closeBack'
      }],
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        'condition[gitName|like]': null,
        dictType: null,
        status: null,
      },
      // 表单参数
      form: {
        gitlabId: '' ,
        gitType: 'gitlab' ,
        gitUserName: '' ,
        password: '',
        gitUrl: '',
        branchName: '',
        fieldProp: ''
      },
      formGitlab: {
        gitlabId: '' ,
        gitType: '' ,
        gitHost: '' ,
        userName: '' ,
        password: '',
        gitUrl: '',
        branchName: ''
      },
      // 表单校验
      rules: {
          gitType: [
            { required: true, message: '选择仓库类型', trigger: 'blur' },
          ],
        gitUserName: [
            { required: true, message: '请输入用户名', trigger: 'blur' },
          ],
          gitName: [
            { required: true, message: '请输入仓库名称', trigger: 'blur' },
          ],
          gitHost: [
            { required: true, message: '请输入仓库服务器信息', trigger: 'blur' },
          ],
          gitNamespace: [
            { required: true, message: '请选择仓库空间', trigger: 'blur' },
          ],
          accessPrivateToken: [
            { required: true, message: '请输入仓库密钥', trigger: 'blur' },
          ],
          branchName: [
            { required: true, message: '请输入分支', trigger: 'blur' },
          ],
          gitUrl: [
          { required: true, message: '请输入仓库地址', trigger: 'blur' },
          ],
          password: [
            { required: true, message: '请输入密码', trigger: 'blur' },
          ]
      }

});

const { svnList, activities, queryParams, form, formGitlab, rules } = toRefs(data);

// 页面加载后触发
onMounted(() => {
  getList();
})

/** 查询仓库类型列表 */
function  getList() {
  loading.value = true;
  listBuildGit(queryParams.value).then(response => {
    typeList.value = response.rows;
    total.value = response.total;
    loading.value = false;
  });
}

// 取消按钮
function cancel() {
  open.value = false;
  reset();
}

// 表单重置
function  reset() {
  form.value = {
      id: null,
      updateTime: null,
      gitType: 'gitlab' ,
      gitUserName: null ,
      password: null,
      gitUrl: null,
      branchName: null,
      gitlabId: null
  };
  proxy.resetForm("formRef");
}

// 获取git 图标
function  giticon(item) {
  const gitType = item.gitType ;
  if( gitType === 'gitea' ){
    return require("@/asserts/images/giticon/gitea.png") ;
  } else if( gitType === 'gitee' ){
    return require("@/asserts/images/giticon/gitee.png") ;
  } else if( gitType === 'gitlab' ){
    return require("@/asserts/images/giticon/gitlab.png") ;
  }
}

    /** 搜索按钮操作 */
function handleQuery() {
  queryParams.value.pageNum = 1;
  getList();
}

/** 重置按钮操作 */
function resetQuery() {
  proxy.resetForm("queryForm");
  handleQuery();
}

// 多选框选中数据
function handleSelectionChange(selection) {
  ids.value = selection.map(item => item.id)
  gitNames.value = selection.map(item => item.gitName)
  single.value = selection.length !== 1
  multiple.value = !selection.length
}

/** 新增按钮操作 */
function handleAdd() {
  reset();
  currentBingGit.value =  gitType
  open.value = true;
  title.value = "添加仓库";
}

/** 修改按钮操作 */
function handleUpdate(row) {
  reset();
  const id = row.id || ids.value
  getBuildGit(id).then(response => {
    form.value = response.data;
    currentBingGit.value = form.value.gitType ;
    form.value.gitlabId = form.value.id ;
    open.value = true;
    title.value = "修改仓库";
  });
}

/** 提交按钮 */
function submitForm() {
  proxy.$refs["formRef"].validate(valid => {
    if (valid) {
      if ( form.value.id != null) {
        updateBuildGit(form.value).then(response => {
          proxy.$modal.msgSuccess("修改成功");
          open.value = false;
          getList();
        });
      } else {
        addBuildGit(form.value).then(response => {
          proxy.$modal.msgSuccess("新增成功");
          open.value = false;
          getList();
        });
      }
    }
  });
}

// 保存submitFormGitlab
function submitFormGitlab() {
  proxy.$refs["formRef"].validate(valid => {
    if ( valid ) {
      bingFormGitlab(form.value).then(response => {
        if ( response.code == 200 ){
          proxy.$modal.msgSuccess("连接仓库测试成功!");
        } else {
          proxy.$modal.$message.error("连接仓库测试失败!"+response);
        }

      });
    }
  });
}

// 绑定 bingGit
function bingGit(row){
  resetGitForm();
  const gitType = row.gitType ;
  if(gitType === 'alicode' || gitType === 'bitbucket'){
    proxy.$modal.$message({
      message: '仓库'+gitType+'绑定暂未开通.',
      type: 'warning'
    });
    return ;
  }

  console.log('git type = ' + gitType) ;
  if( gitType === 'github' || gitType === 'gitee' ){

    const loading = proxy.$modal.$loading({
      lock: true,
      text: 'Loading',
      spinner: 'el-icon-loading',
      background: 'rgba(0, 0, 0, 0.7)'
    });

    getGithubAuthurl(row.gitType).then(res => {
      loading.close() ;

      if( res.code == 200 ){
        githubBingLink.value = res.data ;

        // 配置githu链接
        githubDialogVisible.value = true ;
      }else{
        proxy.$modal.msgSuccess("删除成功");
      }
    }).catch(err => {
        loading.close() ;
    }) ;

  } else if( gitType === 'gitlab' || gitType === 'gitea' ){
    formGitlab.value.gitlabId = row.id ;
    formGitlab.value.gitUrl = row.gitUrl ;
    formGitlab.value.branchName = row.branchName ;
    formGitlab.value.userName = row.gitUserName ;
    formGitlab.value.password = row.password ;
    gitlabDialogVisible.value = true ;
    currentBingGit.value = gitType ;
  }

}

// 解除gith绑定
function unBingGit(row){
  const gitType = row.gitType ;
  proxy.$confirm('是否确认要解绑"' + gitType + '"仓库?', "警告", {
          confirmButtonText: "确定",
          cancelButtonText: "取消",
          type: "warning"
        }).then(function() {
        }).then(() => {
          unBing(row.id , gitType).then(res => {
            console.log('res = ' + res) ;
            if( res.code == 200 ){
              proxy.$modal.msgSuccess("解绑" + gitType+ "成功");
              getList() ;
            }
          }) ;
        })
}

// 关闭github链接
function closeGithub(){
  githubDialogVisible.value = false ;
  gitlabDialogVisible.value = false ;
  getList() ;
}

// 绑定github
function openGithubBing(activity){
  console.log('activity = ' + activity) ;
}

/** 删除按钮操作 */
function handleDelete(row) {
  const ids = row.id || ids.value;
  let gitNames = row.gitName || gitNames.value;
  //避免弹出窗数据太长，只显示前15条数据
  if ( gitNames.length > 15 ) {
    gitNames = gitNames.slice(0,15);
  }
  checkIfUsed(ids).then(res => {
    if ( res.code == 200 ) {
      proxy.$confirm('是否确认删除仓库名称为:'+gitNames+' 数据项?', "警告", {
        confirmButtonText: "确定",
        cancelButtonText: "取消",
        type: "warning"
      }).then(function() {
        return delBuildGit(ids);
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
function handleExport() {
  const queryParams = queryParams.value;
  proxy.$confirm('是否确认导出所有仓库类型数据项?', "警告", {
      confirmButtonText: "确定",
      cancelButtonText: "取消",
      type: "warning"
    }).then(() => {
      exportLoading.value = true;
      return exportBuildGit(queryParams);
    }).then(response => {
      download(response.msg);
      exportLoading.value = false;
    })
}

// 表单重置
function resetGitForm() {
  formGitlab.value = {
    gitlabId: null ,
    gitType: null ,
    gitHost: null ,
    userName: '' ,
    password: '',
    gitUrl: '',
    branchName: ''
  };
  proxy.resetForm("formGitlab");
}

</script>

<style lang="scss" scoped>
.git-icon {
  font-size: 30px;
  color: #005bd4;
}

.bing-btn {
  padding-left:10px !important;
  padding-right: 10px !important;
}

.sc-icons{
  height: 100%;
  font-size: 30px;
  margin-right: 10px;
  color: #005bd4;

  img{
    width: 35px;
    background: #f6f8fc;
    border-radius: 50%;
  }
}

</style>
