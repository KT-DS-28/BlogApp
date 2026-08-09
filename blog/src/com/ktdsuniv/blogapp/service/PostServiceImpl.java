package com.ktdsuniv.blogapp.service;

import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import com.ktdsuniv.blogapp.domain.Comment;
import com.ktdsuniv.blogapp.domain.Post;
import com.ktdsuniv.blogapp.domain.User;
import com.ktdsuniv.blogapp.exception.BlogException;
import com.ktdsuniv.blogapp.exception.DuplicateLikeException;
import com.ktdsuniv.blogapp.exception.NotFoundException;
import com.ktdsuniv.blogapp.exception.NotLoggedInException;
import com.ktdsuniv.blogapp.exception.SelfLikeException;
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
		User user = null; // null 허용
		if (Session.isLoggedIn()) {
			user = Session.getLoginUser();
		}
		
		String ownerId = ScannerUtil.nextLine("블로그 아이디: ").trim();
		if (ownerId == null || ownerId.isBlank()) {
			throw new BlogException("존재하지 않는 아이디입니다.");
		}
		
		User blogOwner = userService.findById(ownerId);
		if (blogOwner == null) {
			throw new BlogException("존재하지 않는 아이디입니다.");
		}
		
		List<Post> posts = getPostList(blogOwner);
		if (posts.size() == 0) {
			System.out.println("조회할 게시글이 없습니다.");
			return;
		}
		
		int index = ScannerUtil.nextInt("조회할 게시글 번호: ");
		if (index < 0 || index >= posts.size()) {
			System.out.println("존재하지 않는 게시글입니다.");
			return;
		}
		
		Post post = posts.get(index);
		boolean isOwner = false;
		
		if (user != null && user == blogOwner) {
			isOwner = true;
		}
		
		if (!isOwner && !post.isPublished()) {
			System.out.println("미발행 게시글은 조회할 수 없습니다.");
			return;
		}
		
		// 조회수 증가
		if(user != null && !post.getViewedUsers().contains(user)) {
			post.getViewedUsers().add(user);
		}
		
		DateTimeFormatter format = DateTimeFormatter.ofPattern("yyyy-MM-dd, HH:mm:ss");
		
		System.out.println("게시글 번호: " + index);
		System.out.println("게시글 제목: " + post.getTitle());
		System.out.println("댓글 수: " + post.getComments().size());
		System.out.println("좋아요 수: " + post.getLikeUsers().size());
		System.out.println("조회 수: " + post.getViewedUsers().size());
		System.out.println("게시 날짜: " + post.getPublishTime().format(format));
		System.out.println("내용: " + post.getContent());
		
		System.out.println("조회한 사용자 목록: ");
		for (User viewUser : post.getViewedUsers()) {
			System.out.println(viewUser.getName());
		}
		
		System.out.println("좋아요 한 사용자 목록: ");
		for (User likeUser : post.getLikeUsers()) {
			System.out.println(likeUser.getName());
		}
		
		System.out.println("댓글 목록: ");
		for(Comment comment: post.getComments()) {
			System.out.println(comment.getAuthor().getName() +": " + 
		comment.getContent() + " (작성 날짜: " + comment.getAddTime().format(format) + ", 좋아요 수: " + 
					comment.getLikeUsers().size() + ")");
		}
		
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
		
		
		// 로그인 상태 확인.
		if (!Session.isLoggedIn()) {
			throw new NotLoggedInException();
		}
		
		User user = Session.getLoginUser();
		// 블로그 선택
	    System.out.println("좋아요 할 글이 있는 블로그를 번호로 입력하세요.");
	    
	    // 모든 게시판 사용자 리스트.
		List<User> users = userService.getAllUsers();
		
		// 선택 대상 출력.
	    for (int i = 0; i < users.size(); i++) {
	    	System.out.println(i + ". " + users.get(i).getBlogName());
	    }
	    
	    int userIndex = ScannerUtil.nextInt("블로그 번호: ");
	    
	    // 없는 번호의 블로그를 선택하면 에러 발생.
	    if (userIndex < 0 || userIndex >= users.size()) {
	    	throw new BlogException("존재하지 않는 블로그입니다.");
	    }
	    
	    
	    User blogOwner = users.get(userIndex);
	    
	    //게시글 선택
	    List<Post> posts = getPostList(blogOwner);
	    
	    
	    // 선택한 블로그에 게시물이 없으면 종료
	    if (posts.isEmpty()) {
	    	throw new BlogException("선택한 블로그에는 게시물이 없습니다.");
	    }
	    
	    // 좋아요 할 대상 리스트 출력.
		for (int i = 0; i < posts.size(); i++) {
			System.out.println(i + "." + posts.get(i).getTitle());
		}
		
		System.out.println("좋아요 할 게시글 번호를 선택 해 주세요.");
		
		int postIndex = ScannerUtil.nextInt("게시글 번호: ");
		
		if (postIndex < 0 || postIndex >= posts.size()) {
			throw new BlogException("존재하지 않는 게시글 번호입니다.");
		} 
		
		Post post = posts.get(postIndex);
		
		if (post.getAuthor() == user) {
			throw new SelfLikeException();
		}
		
		if (post.getLikeUsers().contains(user)) {
			throw new DuplicateLikeException();
		}
		
		post.getLikeUsers().add(user);
		
		System.out.println("좋아요를 했습니다.");
		
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
