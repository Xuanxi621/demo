<template>
    <div class="typewriter-page">
        <!-- 标题 -->
        <h1 class="page-title">AI 打字机助手</h1>

        <!-- 内容区 -->
        <div class="content-wrap">
            <!-- 输入框 -->
            <textarea v-model="inputText" class="input-box" placeholder="输入你的问题或文字..." rows="5"></textarea>

            <!-- 按钮区 -->
            <div class="btn-group">
                <button @click="startType" :disabled="isRunning" class="btn start-btn">
                    开始打字
                </button>
                <button @click="clearAll" class="btn clear-btn">
                    清空
                </button>
            </div>

            <!-- 打字机效果展示区 -->
            <div class="result-box">
                <p class="type-result">
                    {{ typedText }}
                    <span class="cursor" v-if="isRunning"></span>
                </p>
            </div>
        </div>
    </div>
</template>

<script setup>
import axios from 'axios'
import { ref, onUnmounted } from 'vue'

const inputText = ref('')
const typedText = ref('')
const isRunning = ref(false)
let typeTimer = null
let currentIndex = ref(0)

/**
 * ✅ 调用后端 /chat 接口
 *   使用 query 参数 content=xxx
 */
const fetchAIResponse = async (text) => {
    try {
        const res = await axios.post(`http://localhost:8080/api/chat?content=${encodeURIComponent(text)}`)
        console.log(res)
        // DashScope 返回格式兼容 OpenAI
        return res.data?.choices?.[0]?.message?.content || '（AI 无回复）'
    } catch (err) {
        console.error('请求出错:', err)
        return '请求出错，请检查后端服务。'
    }
}

/**
 * ✅ 打字机逻辑
 */
const startType = async () => {
    if (!inputText.value.trim() || isRunning.value) return

    typedText.value = 'AI 正在思考...'
    isRunning.value = true

    const aiText = await fetchAIResponse(inputText.value)

    typedText.value = ''
    currentIndex.value = 0

    typeTimer = setInterval(() => {
        if (currentIndex.value < aiText.length) {
            typedText.value += aiText[currentIndex.value]
            currentIndex.value++
        } else {
            clearInterval(typeTimer)
            isRunning.value = false
        }
    }, 50)
}

/**
 * ✅ 清空
 */
const clearAll = () => {
    clearInterval(typeTimer)
    inputText.value = ''
    typedText.value = ''
    isRunning.value = false
    currentIndex.value = 0
}

/**
 * ✅ 页面卸载清理
 */
onUnmounted(() => {
    clearInterval(typeTimer)
})
</script>

<style scoped>
.typewriter-page {
    max-width: 600px;
    margin: 2rem auto;
    padding: 1rem;
    font-family: "Microsoft YaHei", Arial, sans-serif;
}

.page-title {
    text-align: center;
    color: #333;
    margin-bottom: 1.5rem;
}

.content-wrap {
    display: flex;
    flex-direction: column;
    gap: 1rem;
}

.input-box {
    width: 100%;
    padding: 0.8rem;
    border: 1px solid #ccc;
    border-radius: 6px;
    font-size: 1rem;
    resize: none;
    box-sizing: border-box;
}

.btn-group {
    display: flex;
    gap: 0.8rem;
}

.btn {
    padding: 0.6rem 1.2rem;
    border: none;
    border-radius: 6px;
    cursor: pointer;
    font-size: 0.9rem;
    transition: background-color 0.2s;
}

.start-btn {
    background-color: #4285f4;
    color: white;
}

.start-btn:disabled {
    background-color: #a8c7ff;
    cursor: not-allowed;
}

.clear-btn {
    background-color: #ea4335;
    color: white;
}

.clear-btn:hover {
    background-color: #d33526;
}

.result-box {
    padding: 1rem;
    border: 1px solid #ddd;
    border-radius: 6px;
    min-height: 80px;
    background-color: #f9f9f9;
    white-space: pre-wrap;
    line-height: 1.6;
    font-size: 1rem;
    color: #333;
}

.cursor {
    display: inline-block;
    width: 8px;
    height: 1.2rem;
    background-color: #333;
    margin-left: 2px;
    animation: blink 1s step-end infinite;
}

@keyframes blink {

    from,
    to {
        opacity: 1;
    }

    50% {
        opacity: 0;
    }
}
</style>
