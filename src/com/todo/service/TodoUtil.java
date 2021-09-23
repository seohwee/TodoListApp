package com.todo.service;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.*;

import com.todo.dao.TodoItem;
import com.todo.dao.TodoList;

public class TodoUtil {
	
	public static void createItem(TodoList list) {
		
		String title, desc;
		Scanner sc = new Scanner(System.in);
		
		System.out.print("[항목 추가]\n" + "제목 > ");
		
		title = sc.next();
		if (list.isDuplicate(title)) {
			System.out.println("제목이 중복됩니다!");
			return;
		}
		sc.nextLine();
		
		System.out.print("내용 > ");
		desc = sc.nextLine().trim();
		
		TodoItem t = new TodoItem(title, desc);
		list.addItem(t);
		System.out.println("추가되었습니다.");
	}

	public static void deleteItem(TodoList l) {
		
		Scanner sc = new Scanner(System.in);
		
		System.out.print("[항목 삭제]\n" + "삭제할 항목의 제목을 입력하시오 > ");
		
		String title = sc.next();

		for (TodoItem item : l.getList()) {
			if (title.equals(item.getTitle())) {
				l.deleteItem(item);
				System.out.println("삭제되었습니다.");
				break;
			}
		}
	}


	public static void updateItem(TodoList l) {
		
		Scanner sc = new Scanner(System.in);
		
		System.out.print("[항목 수정]\n" + "수정할 항목의 제목을 입력하시오 > ");
		String title = sc.next().trim();
		if (!l.isDuplicate(title)) {
			System.out.println("없는 제목입니다!");
			return;
		}

		System.out.print("새 제목 > ");
		String new_title = sc.next().trim();
		if (l.isDuplicate(new_title)) {
			System.out.println("제목이 중복됩니다!");
			return;
		}
		sc.nextLine();
		System.out.print("새 내용 > ");
		String new_description = sc.nextLine().trim();
		for (TodoItem item : l.getList()) {
			if (item.getTitle().equals(title)) {
				l.deleteItem(item);
				TodoItem t = new TodoItem(new_title, new_description);
				l.addItem(t);
				System.out.println("수정되었습니다.");
			}
		}

	}

	public static void listAll(TodoList l) {
		System.out.println("[전체 목록]");
		for (TodoItem item : l.getList()) {
			System.out.println(item.toString());
		}
	}

	public static void loadList(TodoList l, String filename) {
		int count=0;
		//File f = new File(filename);

		try {
			BufferedReader br = new BufferedReader(new FileReader(filename));

				String oneline;
				while((oneline = br.readLine()) != null) {
					StringTokenizer st = new StringTokenizer(oneline, "##");
					String title = st.nextToken();
					String desc = st.nextToken();
					
					TodoItem t = new TodoItem(title, desc);
					l.addItem(t);
					count++;
				}
				System.out.printf("%d개의 항목을 읽었습니다.\n", count);

			br.close();
		} catch (FileNotFoundException e) {
				System.out.println("todolist.txt 파일이 없습니다.");
		} catch (IOException e) {
			e.printStackTrace();
		} 
	}

	public static void saveList(TodoList l, String filename) {
		try {
			Writer w = new FileWriter("todolist.txt");
			
			for (TodoItem item : l.getList())  {
				w.write(item.toSaveString());
			}
			w.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} 
		System.out.print("저장되었습니다.");
	}
}
