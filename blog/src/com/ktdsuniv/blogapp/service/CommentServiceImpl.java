package com.ktdsuniv.blogapp.service;

import java.time.format.DateTimeFormatter;
import java.util.List;

import com.ktdsuniv.blogapp.domain.Comment;
import com.ktdsuniv.blogapp.domain.Post;
import com.ktdsuniv.blogapp.domain.User;
import com.ktdsuniv.blogapp.exception.AccessDeniedException;
import com.ktdsuniv.blogapp.exception.BlogException;
import com.ktdsuniv.blogapp.exception.LikedCommentException;
import com.ktdsuniv.blogapp.exception.NotFoundException;
import com.ktdsuniv.blogapp.exception.NotLoggedInException;
import com.ktdsuniv.blogapp.util.ScannerUtil;
import com.ktdsuniv.blogapp.util.Session;

/**
 *
 * 이 파일은 4명이 나눠 쓴다. 자기 담당 메서드 안에서만 작업하고 다른 메서드의 위치나 순서를 바꾸지 말 것. (Git 충돌 방지)
 */
public class CommentServiceImpl implements CommentService {

	private final UserService userService = new UserServiceImpl();
	private final PostService postService = new PostServiceImpl();

	@Override
	public void addComment() {
		// 로그인 여부 확인
		User loginUser = Session.getLoginUser();
		// 전체 사용자 목록 조회
		List<User> users = userService.getAllUsers();
		// 전체 사용자 출력
		for (int i = 0; i < users.size(); i++) {
			System.out.println(i + ". " + users.get(i).getBlogName());
		}
		// 블로그 사용자 주인 선택
		int userIndex = ScannerUtil.nextInt("블로그 번호: ");

		// 알맞은 블로그를 선택했는지 확인
		if (userIndex < 0 || userIndex >= users.size()) {
			throw new BlogException("존재하지 않는 블로그입니다.");
		}

		// 게시글 선택. 발행된 글만 보여준다.
		List<Post> postList = users.get(userIndex).getPostList();
		for (int i = 0; i < postList.size(); i++) {
			if (postList.get(i).isPublished()) {
				System.out.println(i + ". " + postList.get(i).getTitle());
			}
		}

		// 게시글 번호 선택
		int postIndex = ScannerUtil.nextInt("게시글 번호: ");

		// 범위 검사를 먼저 해야 get 이 안전하다.
		if (postIndex < 0 || postIndex >= postList.size()) {
			throw new NotFoundException("게시글");
		}

		if (!postList.get(postIndex).isPublished()) {
			throw new NotFoundException("게시글");
		}

		Post post = postList.get(postIndex);
		// 댓글 입력받기
		String content = ScannerUtil.nextLine("댓글 내용: ").trim();
		// 댓글이 없을 경우
		if (content.isBlank()) {
			throw new BlogException("댓글 내용은 필수로 입력해야 합니다.");
		}

		// 댓글 생성
		Comment comment = new Comment(loginUser, content);
		// 댓글 추가
		post.getComments().add(comment);
		System.out.println("댓글이 등록되었습니다.");
	}

	@Override
	public void updateComment() {
		if (!Session.isLoggedIn()) {
			throw new NotLoggedInException();
		}
		User loginUser = Session.getLoginUser();

		List<User> users = userService.getAllUsers();
		
		// 블로그 선택
		for (int i = 0; i < users.size(); i++) {
			System.out.println(i + ". " + users.get(i).getBlogName());
		}
		
	    int blogNumber = ScannerUtil.nextInt("블로그를 번호로 입력하세요.: ");
	    
	    if (blogNumber < 0 || blogNumber >= users.size()) {
	    	throw new BlogException("존재하지 않는 블로그입니다.");
	    }
	    

	    List<Post> postList = users.get(blogNumber).getPostList();
	    for (int i = 0 ; i < postList.size() ; i++) {
	    	
	    	if (postList.get(i).isPublished()) {
				
	    		System.out.println(i + ". " + postList.get(i).getTitle());
			}
		}
	    
	    int postIndex = ScannerUtil.nextInt("포스트 번호: ");
	    
	    
	    if (postIndex < 0 || postIndex >= postList.size()) {
	    	throw new NotFoundException("게시글");
	    } 
	    
	    if (!postList.get(postIndex).isPublished()) {
	    	throw new NotFoundException("게시글");
	    } 

		Post post = postList.get(postIndex);
		
		if (post.getComments().isEmpty()) {
			throw new BlogException("이 게시글에는 아직 댓글이 없습니다.");
		}
		
		
		for (int i = 0 ; i < post.getComments().size() ; i++) {
	    	 System.out.println(i + ". " + post.getComments().get(i).getContent());
		}

		int commentNumber = ScannerUtil.nextInt("수정할 댓글을 선택하세요.: ");

		if (commentNumber < 0 || commentNumber >= post.getComments().size()) {
			throw new NotFoundException("댓글");
		}

		Comment comment = post.getComments().get(commentNumber);

		if (!comment.getAuthor().getId().equals(loginUser.getId())) {
			throw new AccessDeniedException();
		}

		if (!comment.getLikeUsers().isEmpty()) {
			throw new LikedCommentException();
		}

		String content = ScannerUtil.nextLine("수정 내용을 입력하세요: ").trim();
		if (content.isBlank()) {
			throw new BlogException("내용은 필수로 입력해야 합니다.");
		}

		comment.setContent(content);
		System.out.println("댓글이 수정되었습니다.");
	}

	@Override
	public void deleteComment() {
		if (!Session.isLoggedIn()) {
			throw new NotLoggedInException();
		}
		User loginUser = Session.getLoginUser();

		List<User> users = userService.getAllUsers();

		// 블로그 선택
		for (int i = 0; i < users.size(); i++) {
			System.out.println(i + ". " + users.get(i).getBlogName());
		}

		int blogNumber = ScannerUtil.nextInt("블로그를 번호로 입력하세요.: ");

		if (blogNumber < 0 || blogNumber >= users.size()) {
			throw new BlogException("존재하지 않는 블로그입니다.");
		}

		// 게시글 선택. 발행된 글만 보여준다.
		List<Post> postList = users.get(blogNumber).getPostList();
		for (int i = 0; i < postList.size(); i++) {
			if (postList.get(i).isPublished()) {
				System.out.println(i + ". " + postList.get(i).getTitle());
			}
		}

		int postIndex = ScannerUtil.nextInt("포스트 번호: ");

		// 범위 검사를 먼저 해야 get 이 안전하다.
		if (postIndex < 0 || postIndex >= postList.size()) {
			throw new NotFoundException("게시글");
		}

		if (!postList.get(postIndex).isPublished()) {
			throw new NotFoundException("게시글");
		}

		Post post = postList.get(postIndex);

		if (post.getComments().isEmpty()) {
			throw new BlogException("이 게시글에는 아직 댓글이 없습니다.");
		}

		// 댓글 목록 출력
		for (int i = 0; i < post.getComments().size(); i++) {
			System.out.println(i + ". " + post.getComments().get(i).getContent());
		}

		int commentNumber = ScannerUtil.nextInt("삭제할 댓글을 선택하세요.: ");

		if (commentNumber < 0 || commentNumber >= post.getComments().size()) {
			throw new NotFoundException("댓글");
		}

		Comment comment = post.getComments().get(commentNumber);

		if (!comment.getAuthor().getId().equals(loginUser.getId())) {
			throw new AccessDeniedException();
		}

		if (!comment.getLikeUsers().isEmpty()) {
			throw new LikedCommentException();
		}

		post.getComments().remove(commentNumber);
		System.out.println("댓글이 삭제되었습니다.");
	}
		

	@Override
	public void likeComment() {
		// TODO 엄예진
		if (!Session.isLoggedIn()) {
			throw new NotLoggedInException();
		}

	    User user = Session.getLoginUser();

	    // 블로그 선택
	    System.out.println("좋아요를 하고싶은 블로그를 번호로 입력하세요.");
		List<User> users = userService.getAllUsers();
	    for (int i = 0; i < users.size(); i++) {
	        System.out.println(i + ". " + users.get(i).getBlogName());
	    }

	    int userIndex = ScannerUtil.nextInt("블로그 번호: ");
	    if (userIndex < 0 || userIndex >= users.size()) {
	        throw new BlogException("존재하지 않는 블로그입니다.");
	    }
	    User blogOwner = users.get(userIndex);

	    //게시글 선택
	    List<Post> posts = postService.getPostList(blogOwner);
	    Post post = null;
		for (int i = 0; i < posts.size(); i++) {
			post = posts.get(i);
			if (post.isPublished() || post.getAuthor() == user) {
				System.out.println(i + "." + posts.get(i).getTitle());
			}
		}
		int postIndex = ScannerUtil.nextInt("게시글 번호: ");
		if (postIndex < 0 || postIndex >= posts.size()) {
			System.out.println("존재하지 않는 게시글 번호입니다.");
			return; 
		}
		post = posts.get(postIndex);

		// 댓글 선택
		List<Comment> comments = post.getComments();
		if (comments.size() == 0) {
			System.out.println("댓글이 없습니다.");
		    return;
		}

		//0. 사용자명: 댓글 내용 (댓글 작성 날짜, 댓글 좋아요 수)
		DateTimeFormatter format = DateTimeFormatter.ofPattern("yyyy-MM-dd, HH:mm:ss");
		Comment comment = null;
		for (int i = 0; i < comments.size(); i++) {
			comment = comments.get(i);
		    System.out.println(i + ". " + comment.getAuthor().getName() + 
		    		 " : " + comment.getContent() + " (" + comment.getAddTime().format(format) + 
		    		 ", " + comment.getLikeUsers().size() + ")");
		}
		int commentIndex = ScannerUtil.nextInt("좋아요를 하고 싶은 댓글 번호를 입력하세요. ");
		if (commentIndex < 0 || commentIndex >= comments.size()) {
		    throw new BlogException("존재하지 않는 댓글입니다.");
		}
		comment = comments.get(commentIndex);

		//해당 댓글의 Like<User> 리스트에 사용자를 추가
		if (comment.getAuthor() == user) {
			System.out.println("자신이 작성한 댓글에는 좋아요를 할 수 없습니다.");
			return;
		}

		if(comment.getLikeUsers().contains(user)) {
			System.out.println("한번 좋아요를 한 댓글에는 중복해서 좋아요를 할 수 없습니다.");
			return;
		}

		comment.getLikeUsers().add(user);
		System.out.println("좋아요를 눌렀습니다.");

	}

}
