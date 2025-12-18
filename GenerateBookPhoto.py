import os
from PIL import Image, ImageDraw, ImageFont
import random

def generate_book_covers():
    """
    生成1000张图书封面图片的主函数
    每张图片尺寸为600x900像素，背景颜色随机，中心显示编号信息
    """
    # 创建保存目录
    output_dir = "book_covers"
    if not os.path.exists(output_dir):
        os.makedirs(output_dir)
        print(f"已创建目录: {output_dir}")
    else:
        print(f"目录已存在: {output_dir}")

    # 预定义颜色列表 (RGB值)
    # 这些颜色都是比较柔和的浅色调，适合做背景
    background_colors = [
        (255, 182, 193),  # 浅粉色
        (173, 216, 230),  # 浅蓝色
        (144, 238, 144),  # 浅绿色
        (255, 218, 185),  # 桃色
        (221, 160, 221),  # 梅花色
        (255, 255, 224),  # 浅黄色
        (176, 196, 222),  # 浅钢蓝
        (255, 228, 225),  # 桃色雾
        (230, 230, 250),  # 薰衣草色
        (240, 230, 140),  # 鹿皮色
        (127, 255, 212),  # 春绿色
        (255, 160, 122),  # 灯笼橘
        (102, 205, 170),  # 中海绿色
        (199, 21, 133),   # 深紫红色
        (255, 99, 71),    # 番茄红
    ]

    # 文本颜色（与背景形成对比，确保文字清晰可见）
    text_colors = [
        (0, 0, 0),       # 黑色 - 在浅色背景下很清晰
        (50, 50, 50),    # 深灰色 - 柔和的深色
        (20, 20, 100),   # 深蓝色 - 在浅黄/浅粉背景下清晰
        (100, 0, 100),   # 深紫色 - 在浅绿/浅蓝背景下清晰
        (100, 50, 0),    # 棕色 - 在浅色背景下可用
        (0, 50, 100),    # 深天蓝色 - 在浅黄背景下清晰
        (100, 0, 50),    # 深红色 - 在浅绿背景下清晰
        (0, 100, 100),   # 深青色 - 在浅色背景下清晰
    ]

    # 设置图片尺寸 - 宽600px，高900px，符合常见的书籍封面比例
    width, height = 600, 900
    
    # 尝试加载字体，根据不同操作系统寻找字体文件
    # 这里尝试多种路径以兼容不同系统
    try:
        font = ImageFont.truetype("C:/Windows/Fonts/simsun.ttc", 50)
        print("成功加载 simsun 字体")
    except IOError:
        try:
            font = ImageFont.truetype("/System/Library/Fonts/Arial.ttf", 50)  # macOS
            print("在macOS系统找到 Arial 字体")
        except IOError:
            try:
                font = ImageFont.truetype("C:/Windows/Fonts/msyh.tt", 50)  # Windows
                print("在Windows系统找到微软雅黑字体")
            except IOError:
                # 如果系统中都没有找到Arial字体，则使用默认字体
                font = ImageFont.load_default()
                print("使用默认字体")
                # 尝试重新加载Arial字体（可能是路径问题）
                try:
                    font = ImageFont.truetype("arial.ttf", 50)
                except:
                    # 如果仍然失败，保持使用默认字体
                    print("继续使用默认字体")

    # 生成1000张图片，编号从1到1000
    for i in range(1, 1001):
        # 随机选择背景颜色和文本颜色
        # 使用random.choice()从预定义列表中随机选择
        bg_color = random.choice(background_colors)
        txt_color = random.choice(text_colors)
        
        # 创建新图像对象
        # 'RGB'表示红绿蓝三通道彩色图像
        # color参数设置背景颜色
        img = Image.new('RGB', (width, height), color=bg_color)
        
        # 创建绘图画布对象，用于在图像上绘制文本
        draw = ImageDraw.Draw(img)
        
        # 准备要显示的文本
        # 使用f-string格式化，确保编号为三位数字（如001, 002...）
        text = f"这是一本书的封面，\n编号{i:03d}"
        
        # 计算文本尺寸以便居中
        # textbbox返回文本边界框 (左, 上, 右, 下)
        bbox = draw.textbbox((0, 0), text, font=font)
        text_width = bbox[2] - bbox[0]  # 文本宽度
        text_height = bbox[3] - bbox[1]  # 文本高度
        
        # 计算文本位置使其居中
        # x坐标：(图片总宽度 - 文本宽度) / 2
        # y坐标：(图片总高度 - 文本高度) / 2
        x = (width - text_width) // 2
        y = (height - text_height) // 2
        
        # 在计算出的位置绘制文本
        # fill参数设置文本颜色，font参数设置字体
        draw.text((x, y), text, fill=txt_color, font=font)
        
        # 生成文件名，格式为 cover_XXX.png (如 cover_001.png)
        filename = f"cover_{i:03d}.png"
        
        # 构建完整的文件路径
        filepath = os.path.join(output_dir, filename)
        
        # 保存图片到指定路径
        img.save(filepath)
        
        # 实时输出进度信息
        print(f"已生成: {filename}")

def main():
    """
    主执行函数，调用生成封面的函数并输出完成信息
    """
    print("开始生成图书封面图片...")
    print("图片尺寸: 600x900 像素")
    print("颜色种类: 背景15种，文本8种")
    print("字体大小: 50号")
    print("-" * 50)
    
    generate_book_covers()
    
    print("-" * 50)
    print("\n所有图书封面已生成完成！")
    print("共生成1000张图片")
    print("保存位置: ./book_covers/ 目录下")
    print("文件命名格式: cover_001.png ~ cover_1000.png")

if __name__ == "__main__":
    main()



