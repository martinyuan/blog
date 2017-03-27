package com.yzq.blog.web.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.google.common.base.Function;
import com.google.common.collect.Lists;
import com.yzq.blog.grpc.service.CommentRpcServiceGrpc;
import com.yzq.blog.grpc.service.model.CommentModel.AddCommentRequest;
import com.yzq.blog.grpc.service.model.CommentModel.AddCommentResponse;
import com.yzq.blog.grpc.service.model.CommentModel.Comment;
import com.yzq.blog.grpc.service.model.CommentModel.QueryByBlogNoRequest;
import com.yzq.blog.grpc.service.model.CommentModel.QueryByBlogNoResponse;
import com.yzq.blog.pojo.vo.BlogShowComment;

import io.grpc.Channel;
import net.devh.springboot.autoconfigure.grpc.client.GrpcClient;

@Service
public class CommentGrpcClientService {

	@GrpcClient("micro-comment")
	private Channel serverChannel;

	public List<BlogShowComment> queryByBlogNo(String blogNo) {
		CommentRpcServiceGrpc.CommentRpcServiceBlockingStub stub = CommentRpcServiceGrpc.newBlockingStub(serverChannel);
		QueryByBlogNoRequest request = QueryByBlogNoRequest.newBuilder().setBlogNo(blogNo).build();
		QueryByBlogNoResponse response = stub.queryByBlogNo(request);
		List<Comment> comments = response.getCommentList();
		return transfer(comments);
	}

	public void addComment(String blogNo, String userNo, String content) {
		CommentRpcServiceGrpc.CommentRpcServiceBlockingStub stub = CommentRpcServiceGrpc.newBlockingStub(serverChannel);
		AddCommentRequest request = AddCommentRequest.newBuilder().setBlogNo(blogNo).setUserNo(userNo)
				.setContent(content).build();
		AddCommentResponse response = stub.addComment(request);
		response.getResult();
	}

	private List<BlogShowComment> transfer(List<Comment> comments) {
		return Lists.transform(comments, new Function<Comment, BlogShowComment>() {

			@Override
			public BlogShowComment apply(Comment comment) {
				BlogShowComment c = new BlogShowComment();
				c.setUserName("TODO");
				c.setContent(comment.getContent());
				c.setCreateTime(comment.getCreateTime());
				return c;
			}
		});
	}

}
