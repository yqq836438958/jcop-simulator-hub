package io.grpc.examples.jcop;

import static io.grpc.MethodDescriptor.generateFullMethodName;
import static io.grpc.stub.ClientCalls.asyncBidiStreamingCall;
import static io.grpc.stub.ClientCalls.asyncClientStreamingCall;
import static io.grpc.stub.ClientCalls.asyncServerStreamingCall;
import static io.grpc.stub.ClientCalls.asyncUnaryCall;
import static io.grpc.stub.ClientCalls.blockingServerStreamingCall;
import static io.grpc.stub.ClientCalls.blockingUnaryCall;
import static io.grpc.stub.ClientCalls.futureUnaryCall;
import static io.grpc.stub.ServerCalls.asyncBidiStreamingCall;
import static io.grpc.stub.ServerCalls.asyncClientStreamingCall;
import static io.grpc.stub.ServerCalls.asyncServerStreamingCall;
import static io.grpc.stub.ServerCalls.asyncUnaryCall;
import static io.grpc.stub.ServerCalls.asyncUnimplementedStreamingCall;
import static io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall;

/**
 * <pre>
 * Service Definition
 * </pre>
 */
@javax.annotation.Generated(
    value = "by gRPC proto compiler (version 1.30.0)",
    comments = "Source: jcop.proto")
public final class JCOPSimulatorGrpc {

  private JCOPSimulatorGrpc() {}

  public static final String SERVICE_NAME = "jcop.JCOPSimulator";

  // Static method descriptors that strictly reflect the proto.
  private static volatile io.grpc.MethodDescriptor<io.grpc.examples.jcop.OpenRequest,
      io.grpc.examples.jcop.OpenReply> getOpenMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "Open",
      requestType = io.grpc.examples.jcop.OpenRequest.class,
      responseType = io.grpc.examples.jcop.OpenReply.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<io.grpc.examples.jcop.OpenRequest,
      io.grpc.examples.jcop.OpenReply> getOpenMethod() {
    io.grpc.MethodDescriptor<io.grpc.examples.jcop.OpenRequest, io.grpc.examples.jcop.OpenReply> getOpenMethod;
    if ((getOpenMethod = JCOPSimulatorGrpc.getOpenMethod) == null) {
      synchronized (JCOPSimulatorGrpc.class) {
        if ((getOpenMethod = JCOPSimulatorGrpc.getOpenMethod) == null) {
          JCOPSimulatorGrpc.getOpenMethod = getOpenMethod =
              io.grpc.MethodDescriptor.<io.grpc.examples.jcop.OpenRequest, io.grpc.examples.jcop.OpenReply>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "Open"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  io.grpc.examples.jcop.OpenRequest.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  io.grpc.examples.jcop.OpenReply.getDefaultInstance()))
              .setSchemaDescriptor(new JCOPSimulatorMethodDescriptorSupplier("Open"))
              .build();
        }
      }
    }
    return getOpenMethod;
  }

  private static volatile io.grpc.MethodDescriptor<io.grpc.examples.jcop.Capdu,
      io.grpc.examples.jcop.Rapdu> getTransmitAPDUMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "TransmitAPDU",
      requestType = io.grpc.examples.jcop.Capdu.class,
      responseType = io.grpc.examples.jcop.Rapdu.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<io.grpc.examples.jcop.Capdu,
      io.grpc.examples.jcop.Rapdu> getTransmitAPDUMethod() {
    io.grpc.MethodDescriptor<io.grpc.examples.jcop.Capdu, io.grpc.examples.jcop.Rapdu> getTransmitAPDUMethod;
    if ((getTransmitAPDUMethod = JCOPSimulatorGrpc.getTransmitAPDUMethod) == null) {
      synchronized (JCOPSimulatorGrpc.class) {
        if ((getTransmitAPDUMethod = JCOPSimulatorGrpc.getTransmitAPDUMethod) == null) {
          JCOPSimulatorGrpc.getTransmitAPDUMethod = getTransmitAPDUMethod =
              io.grpc.MethodDescriptor.<io.grpc.examples.jcop.Capdu, io.grpc.examples.jcop.Rapdu>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "TransmitAPDU"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  io.grpc.examples.jcop.Capdu.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  io.grpc.examples.jcop.Rapdu.getDefaultInstance()))
              .setSchemaDescriptor(new JCOPSimulatorMethodDescriptorSupplier("TransmitAPDU"))
              .build();
        }
      }
    }
    return getTransmitAPDUMethod;
  }

  private static volatile io.grpc.MethodDescriptor<io.grpc.examples.jcop.ResetRequest,
      io.grpc.examples.jcop.ResetReply> getResetMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "Reset",
      requestType = io.grpc.examples.jcop.ResetRequest.class,
      responseType = io.grpc.examples.jcop.ResetReply.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<io.grpc.examples.jcop.ResetRequest,
      io.grpc.examples.jcop.ResetReply> getResetMethod() {
    io.grpc.MethodDescriptor<io.grpc.examples.jcop.ResetRequest, io.grpc.examples.jcop.ResetReply> getResetMethod;
    if ((getResetMethod = JCOPSimulatorGrpc.getResetMethod) == null) {
      synchronized (JCOPSimulatorGrpc.class) {
        if ((getResetMethod = JCOPSimulatorGrpc.getResetMethod) == null) {
          JCOPSimulatorGrpc.getResetMethod = getResetMethod =
              io.grpc.MethodDescriptor.<io.grpc.examples.jcop.ResetRequest, io.grpc.examples.jcop.ResetReply>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "Reset"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  io.grpc.examples.jcop.ResetRequest.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  io.grpc.examples.jcop.ResetReply.getDefaultInstance()))
              .setSchemaDescriptor(new JCOPSimulatorMethodDescriptorSupplier("Reset"))
              .build();
        }
      }
    }
    return getResetMethod;
  }

  private static volatile io.grpc.MethodDescriptor<io.grpc.examples.jcop.StopRequest,
      io.grpc.examples.jcop.StopReply> getStopMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "Stop",
      requestType = io.grpc.examples.jcop.StopRequest.class,
      responseType = io.grpc.examples.jcop.StopReply.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<io.grpc.examples.jcop.StopRequest,
      io.grpc.examples.jcop.StopReply> getStopMethod() {
    io.grpc.MethodDescriptor<io.grpc.examples.jcop.StopRequest, io.grpc.examples.jcop.StopReply> getStopMethod;
    if ((getStopMethod = JCOPSimulatorGrpc.getStopMethod) == null) {
      synchronized (JCOPSimulatorGrpc.class) {
        if ((getStopMethod = JCOPSimulatorGrpc.getStopMethod) == null) {
          JCOPSimulatorGrpc.getStopMethod = getStopMethod =
              io.grpc.MethodDescriptor.<io.grpc.examples.jcop.StopRequest, io.grpc.examples.jcop.StopReply>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "Stop"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  io.grpc.examples.jcop.StopRequest.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  io.grpc.examples.jcop.StopReply.getDefaultInstance()))
              .setSchemaDescriptor(new JCOPSimulatorMethodDescriptorSupplier("Stop"))
              .build();
        }
      }
    }
    return getStopMethod;
  }

  /**
   * Creates a new async stub that supports all call types for the service
   */
  public static JCOPSimulatorStub newStub(io.grpc.Channel channel) {
    io.grpc.stub.AbstractStub.StubFactory<JCOPSimulatorStub> factory =
      new io.grpc.stub.AbstractStub.StubFactory<JCOPSimulatorStub>() {
        @java.lang.Override
        public JCOPSimulatorStub newStub(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
          return new JCOPSimulatorStub(channel, callOptions);
        }
      };
    return JCOPSimulatorStub.newStub(factory, channel);
  }

  /**
   * Creates a new blocking-style stub that supports unary and streaming output calls on the service
   */
  public static JCOPSimulatorBlockingStub newBlockingStub(
      io.grpc.Channel channel) {
    io.grpc.stub.AbstractStub.StubFactory<JCOPSimulatorBlockingStub> factory =
      new io.grpc.stub.AbstractStub.StubFactory<JCOPSimulatorBlockingStub>() {
        @java.lang.Override
        public JCOPSimulatorBlockingStub newStub(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
          return new JCOPSimulatorBlockingStub(channel, callOptions);
        }
      };
    return JCOPSimulatorBlockingStub.newStub(factory, channel);
  }

  /**
   * Creates a new ListenableFuture-style stub that supports unary calls on the service
   */
  public static JCOPSimulatorFutureStub newFutureStub(
      io.grpc.Channel channel) {
    io.grpc.stub.AbstractStub.StubFactory<JCOPSimulatorFutureStub> factory =
      new io.grpc.stub.AbstractStub.StubFactory<JCOPSimulatorFutureStub>() {
        @java.lang.Override
        public JCOPSimulatorFutureStub newStub(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
          return new JCOPSimulatorFutureStub(channel, callOptions);
        }
      };
    return JCOPSimulatorFutureStub.newStub(factory, channel);
  }

  /**
   * <pre>
   * Service Definition
   * </pre>
   */
  public static abstract class JCOPSimulatorImplBase implements io.grpc.BindableService {

    /**
     */
    public void open(io.grpc.examples.jcop.OpenRequest request,
        io.grpc.stub.StreamObserver<io.grpc.examples.jcop.OpenReply> responseObserver) {
      asyncUnimplementedUnaryCall(getOpenMethod(), responseObserver);
    }

    /**
     */
    public void transmitAPDU(io.grpc.examples.jcop.Capdu request,
        io.grpc.stub.StreamObserver<io.grpc.examples.jcop.Rapdu> responseObserver) {
      asyncUnimplementedUnaryCall(getTransmitAPDUMethod(), responseObserver);
    }

    /**
     */
    public void reset(io.grpc.examples.jcop.ResetRequest request,
        io.grpc.stub.StreamObserver<io.grpc.examples.jcop.ResetReply> responseObserver) {
      asyncUnimplementedUnaryCall(getResetMethod(), responseObserver);
    }

    /**
     */
    public void stop(io.grpc.examples.jcop.StopRequest request,
        io.grpc.stub.StreamObserver<io.grpc.examples.jcop.StopReply> responseObserver) {
      asyncUnimplementedUnaryCall(getStopMethod(), responseObserver);
    }

    @java.lang.Override public final io.grpc.ServerServiceDefinition bindService() {
      return io.grpc.ServerServiceDefinition.builder(getServiceDescriptor())
          .addMethod(
            getOpenMethod(),
            asyncUnaryCall(
              new MethodHandlers<
                io.grpc.examples.jcop.OpenRequest,
                io.grpc.examples.jcop.OpenReply>(
                  this, METHODID_OPEN)))
          .addMethod(
            getTransmitAPDUMethod(),
            asyncUnaryCall(
              new MethodHandlers<
                io.grpc.examples.jcop.Capdu,
                io.grpc.examples.jcop.Rapdu>(
                  this, METHODID_TRANSMIT_APDU)))
          .addMethod(
            getResetMethod(),
            asyncUnaryCall(
              new MethodHandlers<
                io.grpc.examples.jcop.ResetRequest,
                io.grpc.examples.jcop.ResetReply>(
                  this, METHODID_RESET)))
          .addMethod(
            getStopMethod(),
            asyncUnaryCall(
              new MethodHandlers<
                io.grpc.examples.jcop.StopRequest,
                io.grpc.examples.jcop.StopReply>(
                  this, METHODID_STOP)))
          .build();
    }
  }

  /**
   * <pre>
   * Service Definition
   * </pre>
   */
  public static final class JCOPSimulatorStub extends io.grpc.stub.AbstractAsyncStub<JCOPSimulatorStub> {
    private JCOPSimulatorStub(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected JCOPSimulatorStub build(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      return new JCOPSimulatorStub(channel, callOptions);
    }

    /**
     */
    public void open(io.grpc.examples.jcop.OpenRequest request,
        io.grpc.stub.StreamObserver<io.grpc.examples.jcop.OpenReply> responseObserver) {
      asyncUnaryCall(
          getChannel().newCall(getOpenMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     */
    public void transmitAPDU(io.grpc.examples.jcop.Capdu request,
        io.grpc.stub.StreamObserver<io.grpc.examples.jcop.Rapdu> responseObserver) {
      asyncUnaryCall(
          getChannel().newCall(getTransmitAPDUMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     */
    public void reset(io.grpc.examples.jcop.ResetRequest request,
        io.grpc.stub.StreamObserver<io.grpc.examples.jcop.ResetReply> responseObserver) {
      asyncUnaryCall(
          getChannel().newCall(getResetMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     */
    public void stop(io.grpc.examples.jcop.StopRequest request,
        io.grpc.stub.StreamObserver<io.grpc.examples.jcop.StopReply> responseObserver) {
      asyncUnaryCall(
          getChannel().newCall(getStopMethod(), getCallOptions()), request, responseObserver);
    }
  }

  /**
   * <pre>
   * Service Definition
   * </pre>
   */
  public static final class JCOPSimulatorBlockingStub extends io.grpc.stub.AbstractBlockingStub<JCOPSimulatorBlockingStub> {
    private JCOPSimulatorBlockingStub(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected JCOPSimulatorBlockingStub build(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      return new JCOPSimulatorBlockingStub(channel, callOptions);
    }

    /**
     */
    public io.grpc.examples.jcop.OpenReply open(io.grpc.examples.jcop.OpenRequest request) {
      return blockingUnaryCall(
          getChannel(), getOpenMethod(), getCallOptions(), request);
    }

    /**
     */
    public io.grpc.examples.jcop.Rapdu transmitAPDU(io.grpc.examples.jcop.Capdu request) {
      return blockingUnaryCall(
          getChannel(), getTransmitAPDUMethod(), getCallOptions(), request);
    }

    /**
     */
    public io.grpc.examples.jcop.ResetReply reset(io.grpc.examples.jcop.ResetRequest request) {
      return blockingUnaryCall(
          getChannel(), getResetMethod(), getCallOptions(), request);
    }

    /**
     */
    public io.grpc.examples.jcop.StopReply stop(io.grpc.examples.jcop.StopRequest request) {
      return blockingUnaryCall(
          getChannel(), getStopMethod(), getCallOptions(), request);
    }
  }

  /**
   * <pre>
   * Service Definition
   * </pre>
   */
  public static final class JCOPSimulatorFutureStub extends io.grpc.stub.AbstractFutureStub<JCOPSimulatorFutureStub> {
    private JCOPSimulatorFutureStub(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected JCOPSimulatorFutureStub build(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      return new JCOPSimulatorFutureStub(channel, callOptions);
    }

    /**
     */
    public com.google.common.util.concurrent.ListenableFuture<io.grpc.examples.jcop.OpenReply> open(
        io.grpc.examples.jcop.OpenRequest request) {
      return futureUnaryCall(
          getChannel().newCall(getOpenMethod(), getCallOptions()), request);
    }

    /**
     */
    public com.google.common.util.concurrent.ListenableFuture<io.grpc.examples.jcop.Rapdu> transmitAPDU(
        io.grpc.examples.jcop.Capdu request) {
      return futureUnaryCall(
          getChannel().newCall(getTransmitAPDUMethod(), getCallOptions()), request);
    }

    /**
     */
    public com.google.common.util.concurrent.ListenableFuture<io.grpc.examples.jcop.ResetReply> reset(
        io.grpc.examples.jcop.ResetRequest request) {
      return futureUnaryCall(
          getChannel().newCall(getResetMethod(), getCallOptions()), request);
    }

    /**
     */
    public com.google.common.util.concurrent.ListenableFuture<io.grpc.examples.jcop.StopReply> stop(
        io.grpc.examples.jcop.StopRequest request) {
      return futureUnaryCall(
          getChannel().newCall(getStopMethod(), getCallOptions()), request);
    }
  }

  private static final int METHODID_OPEN = 0;
  private static final int METHODID_TRANSMIT_APDU = 1;
  private static final int METHODID_RESET = 2;
  private static final int METHODID_STOP = 3;

  private static final class MethodHandlers<Req, Resp> implements
      io.grpc.stub.ServerCalls.UnaryMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.ServerStreamingMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.ClientStreamingMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.BidiStreamingMethod<Req, Resp> {
    private final JCOPSimulatorImplBase serviceImpl;
    private final int methodId;

    MethodHandlers(JCOPSimulatorImplBase serviceImpl, int methodId) {
      this.serviceImpl = serviceImpl;
      this.methodId = methodId;
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("unchecked")
    public void invoke(Req request, io.grpc.stub.StreamObserver<Resp> responseObserver) {
      switch (methodId) {
        case METHODID_OPEN:
          serviceImpl.open((io.grpc.examples.jcop.OpenRequest) request,
              (io.grpc.stub.StreamObserver<io.grpc.examples.jcop.OpenReply>) responseObserver);
          break;
        case METHODID_TRANSMIT_APDU:
          serviceImpl.transmitAPDU((io.grpc.examples.jcop.Capdu) request,
              (io.grpc.stub.StreamObserver<io.grpc.examples.jcop.Rapdu>) responseObserver);
          break;
        case METHODID_RESET:
          serviceImpl.reset((io.grpc.examples.jcop.ResetRequest) request,
              (io.grpc.stub.StreamObserver<io.grpc.examples.jcop.ResetReply>) responseObserver);
          break;
        case METHODID_STOP:
          serviceImpl.stop((io.grpc.examples.jcop.StopRequest) request,
              (io.grpc.stub.StreamObserver<io.grpc.examples.jcop.StopReply>) responseObserver);
          break;
        default:
          throw new AssertionError();
      }
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("unchecked")
    public io.grpc.stub.StreamObserver<Req> invoke(
        io.grpc.stub.StreamObserver<Resp> responseObserver) {
      switch (methodId) {
        default:
          throw new AssertionError();
      }
    }
  }

  private static abstract class JCOPSimulatorBaseDescriptorSupplier
      implements io.grpc.protobuf.ProtoFileDescriptorSupplier, io.grpc.protobuf.ProtoServiceDescriptorSupplier {
    JCOPSimulatorBaseDescriptorSupplier() {}

    @java.lang.Override
    public com.google.protobuf.Descriptors.FileDescriptor getFileDescriptor() {
      return io.grpc.examples.jcop.JcopProto.getDescriptor();
    }

    @java.lang.Override
    public com.google.protobuf.Descriptors.ServiceDescriptor getServiceDescriptor() {
      return getFileDescriptor().findServiceByName("JCOPSimulator");
    }
  }

  private static final class JCOPSimulatorFileDescriptorSupplier
      extends JCOPSimulatorBaseDescriptorSupplier {
    JCOPSimulatorFileDescriptorSupplier() {}
  }

  private static final class JCOPSimulatorMethodDescriptorSupplier
      extends JCOPSimulatorBaseDescriptorSupplier
      implements io.grpc.protobuf.ProtoMethodDescriptorSupplier {
    private final String methodName;

    JCOPSimulatorMethodDescriptorSupplier(String methodName) {
      this.methodName = methodName;
    }

    @java.lang.Override
    public com.google.protobuf.Descriptors.MethodDescriptor getMethodDescriptor() {
      return getServiceDescriptor().findMethodByName(methodName);
    }
  }

  private static volatile io.grpc.ServiceDescriptor serviceDescriptor;

  public static io.grpc.ServiceDescriptor getServiceDescriptor() {
    io.grpc.ServiceDescriptor result = serviceDescriptor;
    if (result == null) {
      synchronized (JCOPSimulatorGrpc.class) {
        result = serviceDescriptor;
        if (result == null) {
          serviceDescriptor = result = io.grpc.ServiceDescriptor.newBuilder(SERVICE_NAME)
              .setSchemaDescriptor(new JCOPSimulatorFileDescriptorSupplier())
              .addMethod(getOpenMethod())
              .addMethod(getTransmitAPDUMethod())
              .addMethod(getResetMethod())
              .addMethod(getStopMethod())
              .build();
        }
      }
    }
    return result;
  }
}
