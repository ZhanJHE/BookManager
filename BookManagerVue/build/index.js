// 导入 runjs 库，用于在 Node.js 中执行 shell 命令
const { run } = require('runjs')
// 导入 chalk 库，用于在控制台输出带颜色的文本
const chalk = require('chalk')
// 导入项目的 vue.config.js 配置文件
const config = require('../vue.config.js')
// 获取从命令行传入的原始参数（从第三个参数开始）
const rawArgv = process.argv.slice(2)
// 将参数数组连接成一个字符串
const args = rawArgv.join(' ')

// 检查是否需要启动预览模式
// 可以通过 npm run preview 或在命令中加入 --preview 参数来触发
if (process.env.npm_config_preview || rawArgv.includes('--preview')) {
  // 检查是否需要生成构建报告
  const report = rawArgv.includes('--report')

  // 执行 Vue CLI 的构建命令，并传入所有参数
  run(`vue-cli-service build ${args}`)

  const port = 9526 // 预览服务器的端口号
  const publicPath = config.publicPath // 从 vue.config.js 中获取 publicPath

  var connect = require('connect') // 导入 connect 库，一个轻量的 Node.js 中间件框架
  var serveStatic = require('serve-static') // 导入 serve-static 库，用于提供静态文件服务
  const app = connect() // 创建一个 connect 应用实例

  // 使用中间件来托管构建后的 dist 目录
  app.use(
    publicPath, // 在指定的 publicPath 下提供服务
    serveStatic('./dist', { // 静态文件目录
      index: ['index.html', '/'] // 默认的入口文件
    })
  )

  // 启动服务器并监听指定端口
  app.listen(port, function () {
    console.log(chalk.green(`> 预览地址: http://localhost:${port}${publicPath}`))
    if (report) {
      console.log(chalk.green(`> 构建报告: http://localhost:${port}${publicPath}report.html`))
    }
  })
} else {
  // 如果不是预览模式，则只执行构建命令
  run(`vue-cli-service build ${args}`)
}