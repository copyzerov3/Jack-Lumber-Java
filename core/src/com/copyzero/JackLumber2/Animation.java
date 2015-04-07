package com.copyzero.JackLumber2;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class Animation 
{
	private String m_name;
	private int m_CellHeight,m_CellWidth;
	private int m_NumberOfColumns,m_NumberOfRows;
	private float m_TimePerFrame;
	private Texture sheet;
	private int m_AnimationRow = 0,m_AnimationColumn = 0;
	private boolean flip = false;
	
	public Animation(String Name,String Path,int CellWidth,int CellHeight,int NumberOfRows,int NumberOfColumns,float TimePerFrame)
	{
		sheet = new Texture(Gdx.files.internal(Path));
		m_name = Name;
		m_CellHeight = CellHeight;
		m_CellWidth = CellWidth;
		m_NumberOfColumns = NumberOfColumns;
		m_NumberOfRows = NumberOfRows;
		m_TimePerFrame = TimePerFrame;
	}
	public void Update(float deltaTime)
	{
		
		
		
		if(m_AnimationColumn > m_NumberOfColumns)
		{
			m_AnimationColumn = 0;
		}
	}
	public void Draw(SpriteBatch spriteBatch,int x,int y)
	{
		spriteBatch.draw(sheet, x, y, m_CellWidth, m_CellHeight,m_AnimationColumn*m_CellWidth ,m_AnimationRow*m_CellHeight , m_CellWidth, m_CellHeight, false, flip);
	}
	private void SetAnimationToDraw(int AnimationRow)
	{
		m_AnimationRow = AnimationRow;
	}
	public void SetNumberOfColumns(int NumberOfColumns)
	{
		if(NumberOfColumns> m_NumberOfColumns)
			return;
		m_NumberOfColumns = NumberOfColumns;
	}
	public void SetTimePerFrame(float Time)
	{
		m_TimePerFrame = Time;
	}
	public String GetName()
	{
		return m_name;
	}
	public void Dispose()
	{
		sheet.dispose();
	}

}
