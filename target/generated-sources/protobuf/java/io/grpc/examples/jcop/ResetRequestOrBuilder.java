// Generated by the protocol buffer compiler.  DO NOT EDIT!
// source: jcop.proto

package io.grpc.examples.jcop;

public interface ResetRequestOrBuilder extends
    // @@protoc_insertion_point(interface_extends:jcop.ResetRequest)
    com.google.protobuf.MessageOrBuilder {

  /**
   * <code>string session_id = 1;</code>
   * @return The sessionId.
   */
  java.lang.String getSessionId();
  /**
   * <code>string session_id = 1;</code>
   * @return The bytes for sessionId.
   */
  com.google.protobuf.ByteString
      getSessionIdBytes();

  /**
   * <code>string simulator_id = 2;</code>
   * @return The simulatorId.
   */
  java.lang.String getSimulatorId();
  /**
   * <code>string simulator_id = 2;</code>
   * @return The bytes for simulatorId.
   */
  com.google.protobuf.ByteString
      getSimulatorIdBytes();

  /**
   * <code>bool warm = 3;</code>
   * @return The warm.
   */
  boolean getWarm();
}
