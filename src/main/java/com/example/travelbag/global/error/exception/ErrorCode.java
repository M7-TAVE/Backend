package com.example.travelbag.global.error.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum ErrorCode {
    SAMPLE_ERROR(HttpStatus.BAD_REQUEST, "Sample Error Message"),

    INTERNAL_SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "서버 오류, 관리자에게 문의하세요"),

    ID_TOKEN_VERIFICATION_FAILED(HttpStatus.UNAUTHORIZED, "ID 토큰 검증에 실패했습니다."),
    EXPIRED_JWT_TOKEN(HttpStatus.UNAUTHORIZED, "만료된 JWT 토큰입니다."),
    MISSING_JWT_TOKEN(HttpStatus.UNAUTHORIZED, "토큰 정보가 존재하지 않습니다."),

    AUTH_NOT_FOUND(HttpStatus.UNAUTHORIZED, "시큐리티 인증 정보를 찾을 수 없습니다."),


    MEMBER_NOT_FOUND(HttpStatus.NOT_FOUND, "해당 회원을 찾을 수 없습니다."),
    MEMBER_ALREADY_DELETED(HttpStatus.BAD_REQUEST, "이미 탈퇴한 회원입니다."),

    BAG_NOT_FOUND(HttpStatus.NOT_FOUND, "해당 가방을 찾을 수 없습니다"),
    NOT_BAG_OWNER(HttpStatus.BAD_REQUEST, "해당 가방의 소유자가 아닙니다."),

    TEMPLATE_NOT_FOUND(HttpStatus.NOT_FOUND, "해당 템플릿을 찾을 수 없습니다."),

    ITEM_NOT_FOUND(HttpStatus.NOT_FOUND, "해당 물품을 찾을 수 없습니다."),

    UPDATE_PERMISSION_DENIED(HttpStatus.BAD_REQUEST, "수정할 권한이 없습니다."),
    DELETE_PERMISSION_DENIED(HttpStatus.BAD_REQUEST, "삭제할 권한이 없습니다."),

    DUPLICATE_ITEM(HttpStatus.BAD_REQUEST, "이미 추가된 물품입니다."),

    BAG_NOT_OWNED_BY_MEMBER(HttpStatus.BAD_REQUEST, "가방 없습니다."),

    CATEGORY_NOT_FOUND(HttpStatus.NOT_FOUND, "해당 카테고리를 찾을 수 없습니다.")
    ;

    private final HttpStatus status;
    private final String message;
}