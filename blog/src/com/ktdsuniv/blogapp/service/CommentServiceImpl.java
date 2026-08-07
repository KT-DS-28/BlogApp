package com.ktdsuniv.blogapp.service;

import com.ktdsuniv.blogapp.domain.Comment;
import com.ktdsuniv.blogapp.domain.Post;
import com.ktdsuniv.blogapp.domain.User;
import com.ktdsuniv.blogapp.exception.BlogException;
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
		User author = Session.getLoginUser();

		postService.showPostList();
		int postNumber = ScannerUtil.nextInt("게시물을 선택하세요.: ");
		Post post = postService.getPost(author, postNumber);
// 댓글 개수
		if(post.getComments().isEmpty()) {
			throw new BlogException("내용은 필수로 입력해야 합니다.");
		}
		postService.showPostDetail();
		int commentNumber = ScannerUtil.nextInt("수정할 댓글을 선택하세요.: ");
		Comment comment = post.getComments().get(commentNumber);
// 좋아요확인
		String content = ScannerUtil.nextLine("수정 내용을 입력하세요: ").trim();
		if (content.isBlank()) {
			throw new BlogException("내용은 필수로 입력해야 합니다.");
		}
//		Post post = new Post(author, title, content);
//		author.getPostList().add(post);
//		System.out.println("게시글이 등록되었습니다.");
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
