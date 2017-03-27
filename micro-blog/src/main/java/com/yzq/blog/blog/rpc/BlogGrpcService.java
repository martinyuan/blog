package com.yzq.blog.blog.rpc;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.google.common.base.Function;
import com.google.common.collect.Lists;
import com.yzq.blog.blog.service.BlogService;
import com.yzq.blog.grpc.service.BlogRpcServiceGrpc;
import com.yzq.blog.grpc.service.model.BlogModel.AddBlogRequest;
import com.yzq.blog.grpc.service.model.BlogModel.AddBlogResponse;
import com.yzq.blog.grpc.service.model.BlogModel.QueryByBusNoRequest;
import com.yzq.blog.grpc.service.model.BlogModel.QueryByBusNoResponse;
import com.yzq.blog.grpc.service.model.BlogModel.QueryLastRequest;
import com.yzq.blog.grpc.service.model.BlogModel.QueryLastResponse;
import com.yzq.blog.pojo.dbo.Blog;

import io.grpc.stub.StreamObserver;
import net.devh.springboot.autoconfigure.grpc.server.GrpcService;

@GrpcService(BlogRpcServiceGrpc.class)
public class BlogGrpcService extends BlogRpcServiceGrpc.BlogRpcServiceImplBase {

	@Autowired
	private BlogService blogService;

	@Override
	public void queryLast(QueryLastRequest request, StreamObserver<QueryLastResponse> responseObserver) {
		List<Blog> blogs = blogService.queryLastBlogs(request.getSize());
		QueryLastResponse reply = QueryLastResponse.newBuilder().addAllBlog(transfer(blogs)).build();
		responseObserver.onNext(reply);
		responseObserver.onCompleted();
	}

	@Override
	public void queryByBusNo(QueryByBusNoRequest request, StreamObserver<QueryByBusNoResponse> responseObserver) {
		String busNo = request.getBusNo();
		System.out.println("busNo:"+busNo);
		Blog blog = blogService.queryByBusNo(busNo);
		com.yzq.blog.grpc.service.model.BlogModel.Blog value = transferBlog(blog);
		QueryByBusNoResponse reply = QueryByBusNoResponse.newBuilder().setBlog(value).build();
		responseObserver.onNext(reply);
		responseObserver.onCompleted();
	}

	
	
	@Override
	public void publish(AddBlogRequest request, StreamObserver<AddBlogResponse> responseObserver) {
		String userNo = request.getUserNo();
		String title = request.getTitle();
		String content = request.getContent();
		boolean result = blogService.publish(userNo,title,content);
		AddBlogResponse reply = AddBlogResponse.newBuilder().setResult(result).build();
		responseObserver.onNext(reply);
		responseObserver.onCompleted();
	}

	private com.yzq.blog.grpc.service.model.BlogModel.Blog transferBlog(Blog blog) {
		return com.yzq.blog.grpc.service.model.BlogModel.Blog.newBuilder().setContent(blog.getContent())
				.setTitle(blog.getTitle()).setUserNo(blog.getUserNo()).setBusNo(blog.getBusNo()).build();
	}

	private List<com.yzq.blog.grpc.service.model.BlogModel.Blog> transfer(List<Blog> blogs) {
		return Lists.transform(blogs, new Function<Blog, com.yzq.blog.grpc.service.model.BlogModel.Blog>() {

			@Override
			public com.yzq.blog.grpc.service.model.BlogModel.Blog apply(Blog blog) {
				return transferBlog(blog);
			}

		});
	}

}
