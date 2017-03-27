package com.yzq.blog.user.rpc;

import org.springframework.beans.factory.annotation.Autowired;

import com.yzq.blog.grpc.service.UserRpcServiceGrpc;
import com.yzq.blog.grpc.service.model.UserModel.GetUserInfoRequest;
import com.yzq.blog.grpc.service.model.UserModel.GetUserInfoResponse;
import com.yzq.blog.grpc.service.model.UserModel.LoginRequest;
import com.yzq.blog.grpc.service.model.UserModel.LoginResponse;
import com.yzq.blog.grpc.service.model.UserModel.RegisterRequest;
import com.yzq.blog.grpc.service.model.UserModel.RegisterResponse;
import com.yzq.blog.pojo.dbo.User;
import com.yzq.blog.user.service.UserService;

import io.grpc.stub.StreamObserver;
import net.devh.springboot.autoconfigure.grpc.server.GrpcService;

@GrpcService(UserRpcServiceGrpc.class)
public class UserGrpcService extends UserRpcServiceGrpc.UserRpcServiceImplBase {

	@Autowired
	private UserService userService;

	@Override
	public void register(RegisterRequest request, StreamObserver<RegisterResponse> responseObserver) {
		boolean result = userService.doRegister(request.getName(), request.getPassword());
		RegisterResponse reply = RegisterResponse.newBuilder().setResult(result).build();
		responseObserver.onNext(reply);
		responseObserver.onCompleted();
	}

	@Override
	public void login(LoginRequest request, StreamObserver<LoginResponse> responseObserver) {
		String userNo= userService.checklogin(request.getName(), request.getPassword());
		LoginResponse reply = LoginResponse.newBuilder().setUserNo(userNo).setResult(true).build();
		responseObserver.onNext(reply);
		responseObserver.onCompleted();
	}

	@Override
	public void getUserInfo(GetUserInfoRequest request, StreamObserver<GetUserInfoResponse> responseObserver) {
		User user = userService.getUserInfo(request.getUserNo());
		GetUserInfoResponse reply = GetUserInfoResponse.newBuilder().setUserNo(user.getBusNo()).setName(user.getName()).build();
		responseObserver.onNext(reply);
		responseObserver.onCompleted();
	}

	
}
