package com.ktdsuniv.blogapp.service;

import java.util.ArrayList;
import java.util.List;

import com.ktdsuniv.blogapp.domain.Post;
import com.ktdsuniv.blogapp.domain.User;
import com.ktdsuniv.blogapp.exception.BlogException;
import com.ktdsuniv.blogapp.exception.NotFoundException;
import com.ktdsuniv.blogapp.exception.NotLoggedInException;
import com.ktdsuniv.blogapp.util.ScannerUtil;
import com.ktdsuniv.blogapp.util.Session;

/**
 * 게시글 기능 구현. 구현 방법은 docs/IMPLEMENTATION_GUIDE.md 참고.
 *
 * 이 파일은 5명이 나눠 쓴다. 자기 담당 메서드 안에서만 작업하고
 * 
 * 다른 메서드의 위치나 순서를 바꾸지 말 것. (Git 충돌 방지)
 */
public class PostServiceImpl implements PostService {

	private final UserService userService;

	public PostServiceImpl() {
		this.userService = new UserServiceImpl();
	}

	// ================= 메뉴 기능 =================

	@Override
	public void write() {
		if (!Session.isLoggedIn()) {
			throw new NotLoggedInException();
		}
		User author = Session.getLoginUser();
		
		String title = ScannerUtil.nextLine("제목: ").trim();
		if (title.isBlank()) {
			throw new BlogException("제목은 필수로 입력해야 합니다.");
		}
		
		String content = ScannerUtil.nextLine("내용: ").trim();
		if (content.isBlank()) {
			throw new BlogException("내용은 필수로 입력해야 합니다.");
		}
		
		Post post = new Post(author, title, content);
		author.getPostList().add(post);
		
		System.out.println("게시글이 등록되었습니다.");
	}

	@Override
	public void showPostList() {
		// TODO 김경환
		String id = ScannerUtil.nextLine("블로그 아이디: ").trim();
		if (id.isBlank()) {
			throw new BlogException("아이디는 필수로 입력해야 합니다.");
		}
		User blogOwner = userService.findById(id);
		if(blogOwner == null) {
			throw new NotFoundException("블로그를 찾을 수 없습니다.");
		}
		List<Post> posts = getPostList(blogOwner);
		if (posts.isEmpty()) {
			System.out.println("게시글이 없습니다.");
			return;
		}
		System.out.println(blogOwner.getBlogName() +"님의 블로그");
		for(int i=0; i<posts.size(); i++) {
			Post post =posts.get(i);
			System.out.println(i+"."+post.getTitle() +
					"댓글 :"+post.getComments().size()+"개"+
					"좋아요 :"+post.getLikeUsers().size()+"개"+
					"조회수 :" + post.getViewedUsers().size()+"개");
		}
	}

	@Override
	public void showPostDetail() {
		// TODO 엄예진
	}

	@Override
	public void publish() {
		// TODO 이한결
		if (!Session.isLoggedIn()) {
			throw new NotLoggedInException();
		}
		User user = Session.getLoginUser();
		 
		List<Post> posts = user.getPostList(); 
		if (posts.isEmpty()) {
			System.out.println("작성한 게시글이 없습니다.");
			return;
		}
		
		Post post = null;
		boolean exist = false;
		for (int i = 0; i < posts.size(); i++) {
			post = posts.get(i);
			
			if (!post.isPublished()) {
				 System.out.println("미발행된 게시글입니다.");
				 System.out.println(i + ". " + post.getTitle());
			     System.out.println("미발행 게시글 번호를 선택하세요.");
			     exist=true;
			}
		}
			
		if (!exist) {
			System.out.println("미발행 게시글이 없습니다.");
			return;
		}
			
		System.out.println("발행할 게시글을 번호로 입력하세요.");
		int postNumber = ScannerUtil.nextInt("게시글 번호: ");
			
		if (postNumber < 0 || postNumber >= posts.size()) {
			System.out.println("존재하지 않는 게시글 번호입니다.");
			return; 
		}
				
		post = posts.get(postNumber);
			
		if (post.isPublished()) {
			System.out.println("이미 발행된 게시글입니다.");
			return;
		}
			
		post.publish();
		System.out.println("게시글이 발행되었습니다.");
	}
			     
	@Override
	public void like() {
		// TODO 최형선
	}

	// ============ 다른 기능에서 사용하는 조회 ============

	@Override
	public List<Post> getPostList(User blogOwner) {
		// TODO 김경환
		User loginUser = Session.getLoginUser();
		List<Post> allPosts = blogOwner.getPostList();
		// 본인이 자기 블로그를 보는 경우 -> 전체 반환 
		if (loginUser != null && loginUser.equals(blogOwner)) {
			return allPosts;
		} else {
		// 본인이 아닌 경우 -> 발행된 글만 반환 
		List<Post> result = new ArrayList<>();
		for (int i = 0; i< allPosts.size(); i++) {
			Post post = allPosts.get(i);
			if (post.isPublished()) {
				result.add(post);
			}
		}
		return result;
	}
}
	
	@Override
	public Post getPost(User blogOwner, int index) {
		// TODO 엄예진
		List<Post> posts = blogOwner.getPostList();
		if (index < 0 || index >= posts.size()) {
			return null;
		}
		return posts.get(index);
	}

}
