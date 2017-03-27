package com.yzq.blog.comment.rpc;

import java.util.List;

import org.apache.commons.lang.time.DateFormatUtils;
import org.springframework.beans.factory.annotation.Autowired;

import com.google.common.base.Function;
import com.google.common.collect.Lists;
import com.yzq.blog.comment.service.CommentService;
import com.yzq.blog.grpc.service.CommentRpcServiceGrpc;
import com.yzq.blog.grpc.service.model.CommentModel.AddCommentRequest;
import com.yzq.blog.grpc.service.model.CommentModel.AddCommentResponse;
import com.yzq.blog.grpc.service.model.CommentModel.QueryByBlogNoRequest;
import com.yzq.blog.grpc.service.model.CommentModel.QueryByBlogNoResponse;
import com.yzq.blog.pojo.dbo.Comment;

import io.grpc.stub.StreamObserver;
import net.devh.springboot.autoconfigure.grpc.server.GrpcService;

@GrpcService(CommentRpcServiceGrpc.class)
public class CommentGrpcService extends CommentRpcServiceGrpc.CommentRpcServiceImplBase {

	@Autowired
	private CommentService commentService;

	@Override
	public void queryByBlogNo(QueryByBlogNoRequest request, StreamObserver<QueryByBlogNoResponse> responseObserver) {
		String blogNo = request.getBlogNo();
		List<Comment> comments = commentService.queryByBlogNo(blogNo);
		QueryByBlogNoResponse reply = QueryByBlogNoResponse.newBuilder().addAllComment(transfer(comments)).build();
		responseObserver.onNext(reply);
		responseObserver.onCompleted();
	}

	
	@Override
	public void addComment(AddCommentRequest request, StreamObserver<AddCommentResponse> responseObserver) {
		String blogNo = request.getBlogNo();
		String userNo = request.getUserNo();
		String content = request.getContent();
		boolean result = commentService.addComment(blogNo,userNo,content);
		AddCommentResponse reply = AddCommentResponse.newBuilder().setResult(result).build();
		responseObserver.onNext(reply);
		responseObserver.onCompleted();
	}


	private List<com.yzq.blog.grpc.service.model.CommentModel.Comment> transfer(List<Comment> comments) {
		return Lists.transform(comments, new Function<Comment, com.yzq.blog.grpc.service.model.CommentModel.Comment>() {

			@Override
			public com.yzq.blog.grpc.service.model.CommentModel.Comment apply(Comment comment) {
				return com.yzq.blog.grpc.service.model.CommentModel.Comment.newBuilder().setBlogNo(comment.getBlogNo())
						.setBusNo(comment.getBusNo()).setUserNo(comment.getUserNo()).setContent(comment.getContent())
						.setCreateTime(DateFormatUtils.format(comment.getCreateTime(), "yyyy-MM-dd")).build();
			}

		});
	}

}
