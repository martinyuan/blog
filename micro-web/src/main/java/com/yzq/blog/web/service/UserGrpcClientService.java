package com.yzq.blog.web.service;

import org.springframework.stereotype.Service;

import com.yzq.blog.grpc.service.UserRpcServiceGrpc;
import com.yzq.blog.grpc.service.model.UserModel.GetUserInfoRequest;
import com.yzq.blog.grpc.service.model.UserModel.GetUserInfoResponse;
import com.yzq.blog.grpc.service.model.UserModel.LoginRequest;
import com.yzq.blog.grpc.service.model.UserModel.LoginResponse;
import com.yzq.blog.grpc.service.model.UserModel.RegisterRequest;
import com.yzq.blog.grpc.service.model.UserModel.RegisterResponse;
import com.yzq.blog.pojo.vo.IndexUser;

import io.grpc.Channel;
import net.devh.springboot.autoconfigure.grpc.client.GrpcClient;

@Service
public class UserGrpcClientService {

	@GrpcClient("micro-user")
	private Channel serverChannel;

	public boolean doRegister(String name, String password) {
		UserRpcServiceGrpc.UserRpcServiceBlockingStub stub = UserRpcServiceGrpc.newBlockingStub(serverChannel);
		RegisterRequest request = RegisterRequest.newBuilder().setName(name).setPassword(password).build();
		RegisterResponse response = stub.register(request);
		return response.getResult();
	}

	public String checklogin(String name, String password) {
		UserRpcServiceGrpc.UserRpcServiceBlockingStub stub = UserRpcServiceGrpc.newBlockingStub(serverChannel);
		LoginRequest request = LoginRequest.newBuilder().setName(name).setPassword(password).build();
		LoginResponse response = stub.login(request);
		return response.getUserNo();
	}

	public IndexUser getUserInfoByUserNo(String userNo) {
		UserRpcServiceGrpc.UserRpcServiceBlockingStub stub = UserRpcServiceGrpc.newBlockingStub(serverChannel);
		GetUserInfoRequest request = GetUserInfoRequest.newBuilder().setUserNo(userNo).build();
		GetUserInfoResponse response = stub.getUserInfo(request);
		IndexUser user = new IndexUser();
		user.setName(response.getName());
		user.setUserNo(response.getUserNo());
		return user;
	}

}
