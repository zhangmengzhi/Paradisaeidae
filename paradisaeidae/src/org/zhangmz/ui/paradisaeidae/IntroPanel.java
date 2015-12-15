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

package org.zhangmz.ui.paradisaeidae;

import org.zhangmz.ui.paradisaeidae.utilities.RoundedPanel;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Insets;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import org.jdesktop.animation.timing.Animator;
import org.jdesktop.animation.timing.interpolation.PropertySetter;

/**
 * 刚进入时展示的面板
 * @author Amy Fowler
 * 
 * 修改人：张孟志
 * 修改日期：2015-05-22
 * 修改说明：调整动画效果，后来决定废弃slideTextOut这个方法
 */
public class IntroPanel extends RoundedPanel {
    private JLabel introImage;
    private SlidingLabel introText;
    
    public IntroPanel() {
        setLayout(null);
        setOpaque(false);

        introImage = new JLabel(new ImageIcon(
                Paradisaeidae.class.getResource("resources/images/home_notext.png")));
        introImage.setVerticalAlignment(JLabel.TOP);
        
        introText = new SlidingLabel(new ImageIcon(
                Paradisaeidae.class.getResource("resources/images/home_text.png")));
        introText.setVisible(false);
        
        introImage.addMouseListener(new MouseAdapter() {
            public void mousePressed(MouseEvent event) {
                slideTextIn();
            }
        });
        
        add(introText);
        add(introImage);
    }
    
    @Override
    public void doLayout() {
        Dimension size = getSize();
        Insets insets = getInsets();
        int w = size.width - insets.left - insets.right;
        
        Dimension prefSize = introImage.getPreferredSize();
        introImage.setBounds(0, 0, prefSize.width, prefSize.height);
        
        if (introText.isVisible()) {
            prefSize = introText.getPreferredSize();
            introText.setBounds(introText.getX(), introText.getY(),
                    prefSize.width, prefSize.height);
        }
        
    }
    
    @Override
    public void paint(Graphics g) {
        super.paint(g);
        if (!introText.isVisible()) {
            introText.setLocation(1000, 80);
            introText.setVisible(true);
            slideTextIn();
        }
    }
    
    public void slideTextIn() {
    	/*
		modify by zhangmz 2015-05-22 调整动画滑动样式    	
        Animator animator = new Animator(800, 
        		new PropertySetter(introText, "x", getWidth(), 30));
		*/
    	// org.jdesktop.animation.timing.Animator.Animator(int duration, TimingTarget target)
    	// duration动画使用时间（滑动速度）
        Animator animator = new Animator(800, 
                new PropertySetter(introText, "x", 0, 420));
        animator.setStartDelay(800);
        animator.setAcceleration(.2f);
        animator.setDeceleration(.5f);
        animator.start();
    }
    
    /**
     * 修改人：张孟志
     * 修改日期：2015-05-22
     * 修改说明：调整动画效果，后来决定废弃这个方法
     */
    public void slideTextOut() {
    	/*
		modify by zhangmz 2015-05-22 调整动画滑动样式    	
        Animator animator = new Animator(600, 
                new PropertySetter(introText, "x", introText.getX(), -introText.getWidth()));
        */
        Animator animator = new Animator(3000, 
                new PropertySetter(introText, "x", -introText.getWidth(), 0));
        animator.setStartDelay(10);
        animator.setAcceleration(.5f);
        animator.setDeceleration(.2f);
        animator.start();        
    }
    
    public class SlidingLabel extends JLabel {
        public SlidingLabel(Icon icon) {
            super(icon);
        }
        
        public void setX(int x) {
            setLocation(x, getY());
            revalidate();
        }
    }
}
