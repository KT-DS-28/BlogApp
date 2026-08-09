package com.ktdsuniv.blogapp.service;

import com.ktdsuniv.blogapp.domain.Comment;
import com.ktdsuniv.blogapp.domain.Post;
import com.ktdsuniv.blogapp.domain.User;
import com.ktdsuniv.blogapp.exception.AccessDeniedException;
import com.ktdsuniv.blogapp.exception.BlogException;
import com.ktdsuniv.blogapp.exception.LikedCommentException;
import com.ktdsuniv.blogapp.exception.NotFoundException;
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
	}

	@Override
	public void updateComment() {
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
		// TODO 김경환
	}

	@Override
	public void likeComment() {
		// TODO 엄예진
	}

}
