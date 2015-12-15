/*
 * Copyright 2007 Sun Microsystems, Inc.  All Rights Reserved.
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

package org.zhangmz.ui.paradisaeidae.demos.window;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;

import org.zhangmz.ui.paradisaeidae.DemoProperties;
import org.zhangmz.ui.paradisaeidae.demos.DemoUtilities;
import org.zhangmz.ui.paradisaeidae.demos.ResourceManager;

/**
 * @author aim
 * 
 * 版本：V1.1
 * 修改人：张孟志
 * 修改日期：2015-05-22
 * 修改说明：
 * 			1、汉化；
 * 			2、修改找不到java文件资源的Bug；
 * 
 * 修改人：张孟志
 * 修改日期：2015-05-23
 * 修改说明：
 * 			1、增加一个资源（文件）管理；
 * 
 */
@DemoProperties(
        value = "JWindow Demo",
        category = "顶层容器",
        description = "演示JWindow，一个没有系统边界的顶层容器。",
        sourceFiles = {
        		"org/zhangmz/ui/paradisaeidae/demos/window/resources/WindowDemo.txt"
                // "org/zhangmz/ui/paradisaeidae/demos/window/resources/images/WindowDemo.gif"
                }
)
public class WindowDemo extends JPanel {

	// add by zhangmz 2015-05-23 增加一个资源（文件）管理
    private final ResourceManager resourceManager = new ResourceManager(this.getClass());
    
    private JWindow window;

    private JComponent windowSpaceholder;

    public WindowDemo() {
        initComponents();
    }

    protected void initComponents() {
        window = createWindow();

        setLayout(new BorderLayout());
        add(createControlPanel(), BorderLayout.WEST);
        windowSpaceholder = createWindowSpaceholder(window);
        add(windowSpaceholder, BorderLayout.CENTER);
    }

    protected JComponent createControlPanel() {
        Box controlPanel = Box.createVerticalBox();
        controlPanel.setBorder(new EmptyBorder(8, 8, 8, 8));

        // Create button to control visibility of frame
        JButton showButton = new JButton(resourceManager.getString("Window.show.window"));
        showButton.addActionListener(new ShowActionListener());
        controlPanel.add(showButton);

        return controlPanel;
    }

    private static JComponent createWindowSpaceholder(JWindow window) {
        JPanel windowPlaceholder = new JPanel();
        Dimension prefSize = window.getPreferredSize();
        prefSize.width += 12;
        prefSize.height += 12;
        windowPlaceholder.setPreferredSize(prefSize);

        return windowPlaceholder;
    }

    private static JWindow createWindow() {

        //<snip>Create window
        JWindow window = new JWindow();
        //</snip>

        //<snip>Add a border to the window
        window.getRootPane().setBorder(new LineBorder(Color.BLACK, 1));
        //</snip>

        //<snip>Add window's content
        JLabel label = new JLabel("I have no system border.");
        label.setHorizontalAlignment(JLabel.CENTER);
        label.setPreferredSize(new Dimension(250, 200));
        window.add(label);
        //</snip>

        //<snip>Initialize window's size
        // which will shrink-to-fit its contents
        window.pack();
        //</snip>

        return window;
    }

    public void start() {
        DemoUtilities.setToplevelLocation(window, windowSpaceholder, SwingConstants.CENTER);
        showWindow();
    }

    public void stop() {
        //<snip>Hide window
        window.setVisible(false);
        //</snip>
    }

    public void showWindow() {
        //<snip>Show window
        // if window already visible, then bring to the front
        if (window.isShowing()) {
            window.toFront();
        } else {
            window.setVisible(true);
        }
        //</snip>
    }

    private class ShowActionListener implements ActionListener {
        public void actionPerformed(ActionEvent actionEvent) {
            showWindow();
        }
    }

    public static void main(String args[]) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                JFrame frame = new JFrame();
                WindowDemo demo = new WindowDemo();
                frame.add(demo);
                frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                frame.pack();
                frame.setVisible(true);
                demo.start();
            }
        });
    }
}
