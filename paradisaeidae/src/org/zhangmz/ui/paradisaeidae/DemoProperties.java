/*
 * Copyright 2007-2008 Sun Microsystems, Inc.  All Rights Reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions
 * are met:
 *
 *   - Redistributions of source code must retain the above copyright
 *     notice, this list of conditions and the following disclaimer.
 *
 *   - Redistributions in binary form must reproduce the above copyright
 *     notice, this list of conditions and the following disclaimer in the
 *     documentation and/or other materials provided with the distribution.
 *
 *   - Neither the name of Sun Microsystems nor the names of its
 *     contributors may be used to endorse or promote products derived
 *     from this software without specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS
 * IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO,
 * THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR
 * PURPOSE ARE DISCLAIMED.  IN NO EVENT SHALL THE COPYRIGHT OWNER OR
 * CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL,
 * EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO,
 * PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR
 * PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF
 * LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING
 * NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS
 * SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */

package org.zhangmz.ui.paradisaeidae;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * 
 * @ClassName:DemoProperties 
 * @Description:模块注解类
 * @author:张孟志
 * @date:2015-5-22 上午8:28:55 
 * @version V1.0
 * 说明：模块注解类，用于定义模块的属性
 *      value         Demo的名称。DemoSelectorPanel上只显示空格前的字符，例如：Frame Demo将显示Frame。
 *      category      分类的类别名称，相同名称的为同一类别；还标识了配置文件名。
 *      description   描述说明文字。鼠标移动到该Demo上时的提示信息。
 *      iconFile      图标文件
 *      sourceFiles   资源文件数组，将在Source窗口加载。可以用来展示Demo操作的提示信息。
 */
@Retention(RetentionPolicy.RUNTIME)
public @interface DemoProperties {
	
	// Demo的名称。DemoSelectorPanel上只显示空格前的字符，例如：Frame Demo将显示Frame。
    String value(); 
    
    // 分类的类别名称，相同名称的为同一类别；还标识了配置文件名。
    String category();
    
    // 描述说明文字。鼠标移动到该Demo上时的提示信息。
    String description();
    
    // 图标文件
    String iconFile() default ""; 
    
    // 资源文件数组，将在Source窗口加载。可以用来展示Demo操作的提示信息。
    String[] sourceFiles() default "";
}
