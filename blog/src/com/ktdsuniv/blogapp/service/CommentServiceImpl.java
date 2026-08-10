package com.ktdsuniv.blogapp.service;

import java.time.format.DateTimeFormatter;
import java.util.List;

import com.ktdsuniv.blogapp.domain.Comment;
import com.ktdsuniv.blogapp.domain.Post;
import com.ktdsuniv.blogapp.domain.User;
import com.ktdsuniv.blogapp.exception.AccessDeniedException;
import com.ktdsuniv.blogapp.exception.BlogException;
import com.ktdsuniv.blogapp.exception.DuplicateLikeException;
import com.ktdsuniv.blogapp.exception.LikedCommentException;
import com.ktdsuniv.blogapp.exception.NotFoundException;
import com.ktdsuniv.blogapp.exception.NotLoggedInException;
import com.ktdsuniv.blogapp.exception.SelfLikeException;
import com.ktdsuniv.blogapp.util.ScannerUtil;
import com.ktdsuniv.blogapp.util.Session;

/**
 * 댓글 기능 구현. 구현 방법은 docs/IMPLEMENTATION_GUIDE.md 참고.
 *
 * 이 파일은 4명이 나눠 쓴다. 자기 담당 메서드 안에서만 작업하고 다른 메서드의 위치나 순서를 바꾸지 말 것. (Git 충돌 방지)
 */
public class CommentServiceImpl implements CommentService {

	private final UserService userService = new UserServiceImpl();
	private final PostService postService = new PostServiceImpl();

	@Override
	public void addComment() {
		// TODO 최미서
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

		User blogOwner = users.get(userIndex);
		// 블로그 사용자의 게시물 리스트 조회
		List<Post> posts = postService.getPostList(blogOwner);
		// 블로그 사용자의 게시글 제목 출력
		for (int i = 0; i < posts.size(); i++) {
			System.out.println(i + "." + posts.get(i).getTitle());
		}
		// 게시글 번호 선택
		int postIndex = ScannerUtil.nextInt("게시글 번호: ");

		// 선택한 게시글 들고오기
		Post post = postService.getPost(blogOwner, postIndex);
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

		postService.showPostList();
		int postNumber = ScannerUtil.nextInt("게시물을 선택하세요.: ");
		Post post = postService.getPost(loginUser, postNumber);

		if (post == null) {
			throw new NotFoundException("게시글");
		}

		List<Comment> commentList = post.getComments();
		if (commentList.isEmpty()) {
			throw new BlogException("등록된 댓글이 없습니다.");
		}

		for (int i = 0; i < commentList.size(); ++i) {
			System.out.println(i + ". " + commentList.get(i));
		}
		int commentNumber = ScannerUtil.nextInt("수정할 댓글을 선택하세요.: ");

		if (commentNumber < 0 || commentNumber >= commentList.size()) {
			throw new NotFoundException("댓글");
		}

		Comment comment = commentList.get(commentNumber);

		if (!comment.getAuthor().equals(loginUser)) {
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

		postService.showPostList();
		int postNumber = ScannerUtil.nextInt("게시물을 선택하세요.: ");
		Post post = postService.getPost(loginUser, postNumber);

		if (post == null) {
			throw new NotFoundException("게시글");
		}

		if (post.getComments().isEmpty()) {
			throw new BlogException("등록된 댓글이 없습니다.");
		}

		postService.showPostDetail();
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

		// 게시글 선택
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

		// 0. 사용자명: 댓글 내용 (댓글 작성 날짜, 댓글 좋아요 수)
		DateTimeFormatter format = DateTimeFormatter.ofPattern("yyyy-MM-dd, HH:mm:ss");
		Comment comment = null;
		for (int i = 0; i < comments.size(); i++) {
			comment = comments.get(i);
			System.out.println(i + ". " + comment.getAuthor().getName() + " : " + comment.getContent() + " ("
					+ comment.getAddTime().format(format) + ", " + comment.getLikeUsers().size() + ")");
		}
		int commentIndex = ScannerUtil.nextInt("좋아요를 하고 싶은 댓글 번호를 입력하세요. ");
		if (commentIndex < 0 || commentIndex >= comments.size()) {
			throw new BlogException("존재하지 않는 댓글입니다.");
		}
		comment = comments.get(commentIndex);

		// 해당 댓글의 Like<User> 리스트에 사용자를 추가
		if (comment.getAuthor() == user) {
			throw new SelfLikeException();
		}

		if (comment.getLikeUsers().contains(user)) {
			throw new DuplicateLikeException();
		}

		comment.getLikeUsers().add(user);
		System.out.println("좋아요를 눌렀습니다.");

	}

}
