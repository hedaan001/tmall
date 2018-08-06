package tmall.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import tmall.bean.Category;
import tmall.util.DBUtil;

public class CategoryDAO {
	public int getTotal() {
		int total = 0;
		/*���ӵ����ݿ⣨Connection������������ָ�Statement����ִ�в�ѯָ�executeQuery������ò�ѯ�����ResultSet���ȡ�
		
		�����ࣨstatement�������μ̳ж�����
		��1��Statement��������ִ�в��������ļ򵥵�SQL��䣻Statement�ӿ��ṩ��ִ�����ͻ�ȡ����Ļ���������

		��2��PerparedStatement��������ִ�д��򲻴�IN������Ԥ����SQL��䣻PeraredStatement�ӿ���Ӵ���IN�����ķ�����

		��3��CallableStatement��������ִ�ж����ݿ��Ѵ洢���̵ĵ��ã�CallableStatement��Ӵ���OUT�����ķ���
		
		Statement�ṩ����෽������õķ������£�
		��1��execute()������������䣬�����Ƿ��н������

		��2��executeQuery()���������в�ѯ��䣬����ReaultSet����

		��3��executeUpdata()���������и��²��������ظ��µ�������

		��4��addBatch()������������������䡣

		��5��executeBatch()������ִ����������䡣

		��6��clearBatch()�����������������䡣*/
		try (Connection c = DBUtil.getConnection(); Statement s = c.createStatement();) {
			
			String sql = "select count(*) from Category";
			
			ResultSet rs = s.executeQuery(sql);//rs�����ݼ�
			while (rs.next()) {                //��ȡ��һ��
				total = rs.getInt(1);          //ͨ����������������ȡĳһ�е�ֵ
			}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return total;
	}
	
	public void add(Category bean) {
		
		String sql = "insert into category values(null,?)";
		try (Connection c = DBUtil.getConnection(); PreparedStatement ps = c.prepareStatement(sql);) {
			
			ps.setString(1, bean.getName());         //��һ����������ָ�ĸ����������ڶ��������滻��ֵ
			ps.execute();
			ResultSet re = ps.getGeneratedKeys();    //��ȡ����
			
			if (re.next()) {
				int id = re.getInt(1);
				bean.setId(id);
			}
			
		} catch (SQLException e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}
	
	public void update(Category bean) {
		
		String sql = "update category set name= ? where id = ?";
		try (Connection c = DBUtil.getConnection(); PreparedStatement ps = c.prepareStatement(sql);) {
			
			ps.setString(1, bean.getName());
			ps.setInt(2, bean.getId());                           //���õڼ�������
			
			ps.execute();
			
		} catch (SQLException e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		
	}
	
	public void delete(int id) {
		try (Connection c = DBUtil.getConnection(); Statement s = c.createStatement();) {
			
			String sql = "delete form Category where id = " + id;
			s.execute(sql);
			
		} catch (SQLException e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		
	}
	
	public Category get(int id) {
		Category bean = null;
		try (Connection c = DBUtil.getConnection(); Statement s = c.createStatement();){
			String sql = "select * from category where id = " + id;
			
			ResultSet rs = s.executeQuery(sql);
			
			if(rs.next()) {
				bean = new Category();
				String name = rs.getString(2);//��ȡ�ڶ��е�����
				bean.setName(name);
				bean.setId(id);
			}
			
		} catch (SQLException e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return bean;
		
	}
	
	public List<Category> list() {
		return list(0, Short.MAX_VALUE);
	}

	public List<Category> list(int start, int count) {
		// TODO Auto-generated method stub
		List<Category> beans = new  ArrayList<Category>();
		
		String sql = "select * from category order by id desc limit ?,?";
		
		try (Connection c = DBUtil.getConnection(); PreparedStatement ps = c.prepareStatement(sql);) {
			
			ps.setInt(1, start);
			ps.setInt(2, count);
			
			ResultSet rs = ps.executeQuery();
			
			while (rs.next()) {
				Category bean = new Category();
				int id = rs.getInt(1);
				String name = rs.getString(2);
				bean.setId(id);
				bean.setName(name);
				beans.add(bean);
			}
		} catch (SQLException e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		
		return beans;
	}

}
