/*
 * This file is part of GraphStream.
 * 
 * GraphStream is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 * 
 * GraphStream is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License
 * along with GraphStream.  If not, see <http://www.gnu.org/licenses/>.
 * 
 * Copyright 2006 - 2009
 * 	Julien Baudry
 * 	Antoine Dutot
 * 	Yoann Pigné
 * 	Guilhelm Savin
 */
package org.miv.graphstream.tool.workbench.gui;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Insets;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;

import javax.swing.DefaultListModel;
import javax.swing.JList;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ListCellRenderer;

import org.miv.graphstream.tool.workbench.WCore;
import org.miv.graphstream.tool.workbench.WHistory;
import org.miv.graphstream.tool.workbench.WNotificationServer;
import org.miv.graphstream.tool.workbench.event.NotificationListener;

public class WHistoryGUI 
	extends JPanel
	implements NotificationListener
{
	private static final long serialVersionUID = 0x0001L;
	
	static final String TITLE_FONT_R = "org/miv/graphstream/tool/workbench/ressources/larabief.ttf";
	static final String DESCRIPTION_FONT_R = "org/miv/graphstream/tool/workbench/ressources/larabieb.ttf";
	
	static Font TITLE_FONT = null;
	static Font DESCRIPTION_FONT = null;
	
	static
	{
		try
		{
			TITLE_FONT = Font.createFont( Font.TRUETYPE_FONT, 
					ClassLoader.getSystemResourceAsStream(TITLE_FONT_R)).deriveFont(Font.BOLD,16.0f);
			DESCRIPTION_FONT = Font.createFont( Font.TRUETYPE_FONT, 
					ClassLoader.getSystemResourceAsStream(DESCRIPTION_FONT_R)).deriveFont(16.0f);
		}
		catch( Exception e )
		{
			e.printStackTrace();
		}
	}
	
	static class HistoryActionRenderer
		extends JPanel
		implements ListCellRenderer
	{
		private static final long serialVersionUID = 0x0001L;
		
		JLabel 	icon;
		JLabel	title;
		JLabel	description;
		
		public HistoryActionRenderer()
		{
			icon 		= new JLabel();
			title 		= new JLabel();
			description = new JLabel();
			
			title.setFont(TITLE_FONT);
			description.setFont(DESCRIPTION_FONT);
			
			GridBagLayout bag = new GridBagLayout();
			GridBagConstraints c = new GridBagConstraints();
			
			setLayout( bag );
			
			c.anchor = GridBagConstraints.WEST;
			c.gridwidth = 1;
			c.gridheight = 2;
			c.weighty = 1.0;
			
			icon.setPreferredSize( new Dimension(32,32) );
			bag.setConstraints(icon,c);
			add(icon);
			
			c.fill = GridBagConstraints.BOTH;
			
			c.gridwidth = GridBagConstraints.REMAINDER;
			c.gridheight = 1;
			c.weighty = 0.0;
			c.weightx = 3.0;
			
			bag.setConstraints(title,c);
			add(title);
			
			c.insets = new Insets(0,10,0,0);
			
			bag.setConstraints(description,c);
			add(description);
		}
		
		public Component getListCellRendererComponent( JList list, Object value,
				int index, boolean isSelected, boolean hasFocus )
		{
			if( value instanceof WHistory.DelNodeHistoryAction )
			{
				icon.setIcon( WUtils.getImageIcon("action:node_del") );
				title.setText("Del node");
				description.setText(((WHistory.AbstractAddElementHistoryAction)value).getId());
			}
			else if( value instanceof WHistory.AddNodeHistoryAction )
			{
				icon.setIcon( WUtils.getImageIcon("action:node_add") );
				title.setText("Add node");
				description.setText(((WHistory.AbstractAddElementHistoryAction)value).getId());
			}
			else if( value instanceof WHistory.DelEdgeHistoryAction )
			{
				icon.setIcon( WUtils.getImageIcon("action:edge_del") );
				title.setText("Del edge");
				description.setText(((WHistory.AbstractAddElementHistoryAction)value).getId());
			}
			else if( value instanceof WHistory.AddEdgeHistoryAction )
			{
				icon.setIcon( WUtils.getImageIcon("action:edge_add") );
				title.setText("Add edge");
				description.setText(((WHistory.AbstractAddElementHistoryAction)value).getId());
			}
			
			if(isSelected)
			{
	             setBackground(list.getSelectionBackground());
	             setForeground(list.getSelectionForeground());
	        }
			else
			{
	             setBackground(list.getBackground());
	             setForeground(list.getForeground());
	        }

			
			return this;
		}
	}
	
	DefaultListModel model;
	
	public WHistoryGUI()
	{
		model = new DefaultListModel();
		
		JList list = new JList(model);
			list.setCellRenderer( new HistoryActionRenderer() );
		
		setPreferredSize( new Dimension( 200, 300 ) );
		setLayout( new BorderLayout() );
		add( new JScrollPane(list), BorderLayout.CENTER );
		
		WNotificationServer.connect(this);
	}
	
	public void handleNotification( Notification n )
	{
		if( n == Notification.historyUndo )
		{
			
		}
		else if( n == Notification.historyRedo )
		{
			
		}
		else if( n == Notification.historyNew )
		{
			//model.addElement(WCore.getCore().getActiveContext().getHistory().getLast());
			model.add(0,WCore.getCore().getActiveContext().getHistory().getLast());
		}
	}
}
