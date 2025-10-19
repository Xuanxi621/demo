<template>
    <div class="table-container">
        <!-- 错误提示 -->
        <el-alert v-if="error" :title="error" type="error" :closable="false" style="margin-bottom: 20px" />

        <!-- 数据表格 -->
        <el-table :data="tableData" style="width: 80%" :row-class-name="tableRowClassName" border stripe
            empty-text="暂无用户数据">
            <el-table-column prop="id" label="ID" width="80" align="center" />
            <el-table-column prop="userName" label="用户名" width="120" />
            <el-table-column prop="age" label="年龄" align="center" />
        </el-table>
    </div>
</template>

<script lang="ts" setup>
import { onMounted, ref } from 'vue'
import getAccountList from '@/api/account'

// 1. 定义与接口完全匹配的数据类型
interface AccountData {
    id: number
    userName: string
    age: number
}

// 2. 响应式变量初始化（确保tableData始终是数组，避免迭代错误）
const error = ref('')
const tableData = ref<AccountData[]>([])


// 4. 表格行样式（按索引设置警告/成功行）
const tableRowClassName = ({ rowIndex }: { rowIndex: number }) => {
    if (rowIndex === 1) return 'warning-row' // 第2行（索引1）警告色
    if (rowIndex === 3) return 'success-row' // 第4行（索引3）成功色
    return ''
}

// 5. 页面加载时请求数据
onMounted(async () => {

    console.log('请求用户列表接口...')
    const res = await getAccountList()
    console.log('接口响应数据:', res)
    tableData.value = res.data.data // 直接赋值，类型完全匹配

})
</script>

<style scoped>
.table-container {
    padding: 20px;
}

/* 表格行样式 */
.el-table .warning-row {
    --el-table-tr-bg-color: var(--el-color-warning-light-9);
}

.el-table .success-row {
    --el-table-tr-bg-color: var(--el-color-success-light-9);
}
</style>