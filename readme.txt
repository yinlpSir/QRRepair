
技术栈：
	后端：Spring boot | mybatis | spring security | 
	前端：vue3 | element-plus | vite | pinia | vue-router | axios | 


项目参考文档：
	整体参考博客：https://blog.csdn.net/qq_33596978/article/details/85330814

	前端模板借鉴：
		稀土掘金：https://juejin.cn/post/7206163800339365948
		github地址：https://github.com/penndev/veadmin
		模板预览地址：https://penndev.github.io/veadmin/#/login

	二维码开发用的库：
		QuickMedia：一个多媒体处理工具库。有 音频转码、二维码生成和解析、图片处理等。它以plugin方式管理，每个plugin表提供一项对应的服务。需要用时按需引入即可。
			技术文档地址：https://liuyueyi.github.io/quick-media/#/
			github地址：https://github.com/liuyueyi/quick-media



关于项目需求的补充：
	1、通信和协作：系统将提供内部消息和通知功能，以便机房管理员和维修人员之间进行实时沟通和协作。（web socket）
	2、维修历史记录：系统将记录每个设备的维修历史，包括报修时间、维修人员、修复方案和耗时等信息，以便日后参考和分析。
	3、公告模块
	4、用户管理模块



二维码接口笔记：
    接口功能设想：
        每台电脑上贴个相应的二维码，只需扫描二维码即可报修那台电脑，不需要用户输入在哪栋楼、哪个实训室的哪台电脑。只需输入电脑的问题及故障图片然后再提交自己的信息即可报修。
    
    实现思路：
        这里用了 qrcode-plugin这个库实现生成二维码功能。测试环境在整个局域网内，测试设备 手机和电脑。电脑生成二维码，手机扫码报修。
        生成的二维码里的message应该是前端的报修页面的地址，这个地址在整个局域网内的设备都可访问，否则手机扫码就会访问不了报修页面。
        关于订阅后面再说。

    qrcode-plugin：

        两个关键类：
            QrCodeGenWrapper 二维码生成包装类
            QrCodeDeWrapper 二维码解析包装类

        构造二维码，有大量的参数可以进行配置，主要是采用builder设计模式来简化调用，所以有两个配置类需要关注：
            com.github.hui.quick.plugin.qrcode.wrapper.QrCodeOptions：
                这个类接收生成二维码的参数。比如二维码的宽高等
            com.github.hui.quick.plugin.qrcode.wrapper.QrCodeGenWrapper.Builder：
                内部builder构造器，参数和上面的Option保持映射关系。（也就是可链式操作）

        二维码矩阵生成及渲染成图片：
            借助zxing开源库来实现文本信息转换为二维码矩阵。
            可以理解为一个二维数组，数组中元素为0/1, 1表示普通二维码中的小黑点，0为小白点；接下来需要做的就是将矩阵渲染为图片；这里我们主要是借助jdk的awt来实现图片生成

            核心逻辑类：com.github.hui.quick.plugin.qrcode.helper.QrCodeRenderHelper
            
            如果需要对输出图片进行定制的话，修改这个类中的三个方法：
                /**
                    * 绘制logo图片
                    *
                    * @param qrImg
                    * @param logoOptions
                    * @return
                */
                public static BufferedImage drawLogo(BufferedImage qrImg, QrCodeOptions.LogoOptions logoOptions);

                /**
                    * 绘制背景图
                    *
                    * @param qrImg        二维码图
                    * @param bgImgOptions 背景图信息
                    * @return
                */
                public static BufferedImage drawBackground(BufferedImage qrImg, QrCodeOptions.BgImgOptions bgImgOptions);

                /**
                    * 根据二维码矩阵，生成对应的二维码推片
                    *
                    * @param qrCodeConfig
                    * @param bitMatrix
                    * @return
                */
                public static BufferedImage drawQrInfo(QrCodeOptions qrCodeConfig, BitMatrixEx bitMatrix);

		自定义二维码相关功能有：
			a、二维码圆角设置
			b、二维码颜色指定
			c、带logo二维码生成(圆角or圆形等logo)
			d、指定背景图
				有三种样式
			e、探测图形样式指定
			f、探测图形样式指定
			g、动态二维码

            