package com.yzq.blog.web.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.google.common.base.Function;
import com.google.common.collect.Lists;
import com.yzq.blog.grpc.service.BlogRpcServiceGrpc;
import com.yzq.blog.grpc.service.model.BlogModel.AddBlogRequest;
import com.yzq.blog.grpc.service.model.BlogModel.AddBlogResponse;
import com.yzq.blog.grpc.service.model.BlogModel.Blog;
import com.yzq.blog.grpc.service.model.BlogModel.QueryByBusNoRequest;
import com.yzq.blog.grpc.service.model.BlogModel.QueryByBusNoResponse;
import com.yzq.blog.grpc.service.model.BlogModel.QueryLastRequest;
import com.yzq.blog.grpc.service.model.BlogModel.QueryLastResponse;
import com.yzq.blog.pojo.vo.BlogShowVo;

import io.grpc.Channel;
import net.devh.springboot.autoconfigure.grpc.client.GrpcClient;

@Service
public class BlogGrpcClientService {

	@GrpcClient("micro-blog")
	private Channel serverChannel;

	public List<com.yzq.blog.pojo.dbo.Blog> queryLast(int size) {
		BlogRpcServiceGrpc.BlogRpcServiceBlockingStub stub = BlogRpcServiceGrpc.newBlockingStub(serverChannel);
		QueryLastRequest request = QueryLastRequest.newBuilder().setSize(size).build();
		QueryLastResponse response = stub.queryLast(request);
		List<Blog> blogs = response.getBlogList();
		return transfer(blogs);
	}

	private List<com.yzq.blog.pojo.dbo.Blog> transfer(List<Blog> blogs) {
		return Lists.transform(blogs, new Function<Blog, com.yzq.blog.pojo.dbo.Blog>() {

			@Override
			public com.yzq.blog.pojo.dbo.Blog apply(Blog blog) {
				com.yzq.blog.pojo.dbo.Blog b = new com.yzq.blog.pojo.dbo.Blog();
				b.setBusNo(blog.getBusNo());
				b.setUserNo(blog.getUserNo());
				b.setTitle(blog.getTitle());
				b.setContent(blog.getContent());
				return b;
			}
		});
	}

	public BlogShowVo queryByBusNo(String blogNo) {
		BlogRpcServiceGrpc.BlogRpcServiceBlockingStub stub = BlogRpcServiceGrpc.newBlockingStub(serverChannel);
		QueryByBusNoRequest request = QueryByBusNoRequest.newBuilder().setBusNo(blogNo).build();
		QueryByBusNoResponse response = stub.queryByBusNo(request);
		BlogShowVo vo = transferBlog(response.getBlog());
		return vo;
	}

	private BlogShowVo transferBlog(Blog blog) {
		BlogShowVo vo = new BlogShowVo();
		vo.setBlogNo(blog.getBusNo());
		vo.setContent(blog.getContent());
		vo.setTitle(blog.getTitle());
		return vo;
	}

	public void publish(String userNo, String title, String content) {
		System.out.println("userNo:"+userNo);
		System.out.println("title:"+title);
		System.out.println("content:"+content);
		BlogRpcServiceGrpc.BlogRpcServiceBlockingStub stub = BlogRpcServiceGrpc.newBlockingStub(serverChannel);
		AddBlogRequest request = AddBlogRequest.newBuilder().setUserNo(userNo).setTitle(title).setContent(content).build();
		AddBlogResponse response = stub.publish(request);
		response.getResult();
	}

}
