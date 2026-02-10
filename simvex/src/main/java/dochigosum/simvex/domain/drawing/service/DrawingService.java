package dochigosum.simvex.domain.drawing.service;

import dochigosum.simvex.domain.drawing.entity.Conversation;
import dochigosum.simvex.domain.drawing.entity.Drawing;
import dochigosum.simvex.domain.drawing.entity.DrawingPart;
import dochigosum.simvex.domain.drawing.exception.DrawingErrorCode;
import dochigosum.simvex.domain.drawing.presentation.dto.response.DrawingAssetResponse;
import dochigosum.simvex.domain.drawing.presentation.dto.response.*;
import dochigosum.simvex.domain.drawing.repository.ConversationRepository;
import dochigosum.simvex.domain.drawing.repository.DrawingPartRepository;
import dochigosum.simvex.domain.drawing.repository.DrawingRepository;
import dochigosum.simvex.domain.member.entity.Member;
import dochigosum.simvex.domain.member.repository.MemberRepository;
import dochigosum.simvex.global.error.exception.SimvexException;
import dochigosum.simvex.global.s3.S3Service;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class DrawingService {

    private final DrawingRepository drawingRepository;
    private final DrawingPartRepository drawingPartRepository;
    private final MemberRepository memberRepository;
    private final S3Service s3Service;

    // 조립도 정보 반환
    public DrawingExplainResponse drawingExplain(Long drawingId) {
        Drawing drawing = drawingRepository.findById(drawingId)
                .orElseThrow(() -> new SimvexException(DrawingErrorCode.NOT_FOUND));
        return DrawingExplainResponse.of(drawing.getName(), drawing.getDetail());
    }

    // 부품 목록 조회
    public List<DrawingPartsResponse> getParts(Long drawingId) {
        return drawingPartRepository.findAllByDrawing_Id(drawingId)
                .stream()
                .map(part -> {
                    String url = s3Service.getDrawingUrl(part.getDrawing().getName(), part.getFileName());
                    return DrawingPartsResponse.from(part, url);
                })
                .toList();
    }

    // 조립도 내 부품 3D 모델 가져오기
    public List<PartsModelResponse> getModel(Long drawingId) {
        List<DrawingPart> parts = drawingPartRepository.findAllByDrawing_Id(drawingId);

        return parts.stream()
                .map(part -> {
                    String url = s3Service.getDrawingUrl(part.getDrawing().getName(), part.getFileName());
                    return PartsModelResponse.from(part, url);
                })
                .toList();
    }

    // 단일 부품 설명 조회
    public PartExplainResponse getPart(Long partId) {
        return PartExplainResponse.from(drawingPartRepository.findById(partId)
                .orElseThrow(() -> new SimvexException(DrawingErrorCode.NOT_FOUND)));
    }

    // 단일 부품 3D 모델 가져오기
    public PartModelUrlResponse getModelUrl(Long partId) {
        DrawingPart part = drawingPartRepository.findById(partId)
                .orElseThrow(() -> new SimvexException(DrawingErrorCode.NOT_FOUND));
        return PartModelUrlResponse.of(s3Service.getDrawingUrl(part.getDrawing().getName(), part.getFileName()));
    }

    @Transactional
    public List<DrawingAssetResponse> getDrawingAssets(Long drawingId) {
        Drawing drawing = drawingRepository.findById(drawingId)
                .orElseThrow(() -> new SimvexException(DrawingErrorCode.NOT_FOUND));

        List<DrawingPart> parts = drawingPartRepository.findAllByDrawing_Id(drawingId);

        return parts.stream()
                .map(part -> {
                    String url = s3Service.getDrawingUrl(drawing.getName(), part.getFileName());
                    return DrawingAssetResponse.of(part, url);
                })
                .toList();
    }

    // todo: 이후 연결
    @Transactional
    public void createDrawingSession(Long templateId, Long userId) {
        DrawingTemplate drawingTemplate = drawingTemplateRepository.findById(templateId)
                .orElseThrow(() -> new SimvexException(DrawingErrorCode.NOT_FOUND));

        DrawingPartTemplate partTemplate = drawingPartTemplateRepository.findById(templateId)
                .orElseThrow(() -> new SimvexException(DrawingErrorCode.NOT_FOUND));

        Member member = memberRepository.getReferenceById(userId);

        Drawing newDrawing = Drawing.builder()
                .member(member)
                .name(drawingTemplate.getName())
                .previewImgUrl(drawingTemplate.getPreviewImgUrl())
                .build();

        Conversation conversation = Conversation.builder()
                .drawing(newDrawing)
                .build();
        newDrawing.addConversation(conversation);

        partTemplate.forEach(tp -> {
            DrawingPart part = DrawingPart.builder()
                    .name(tp.getName())
                    .detail(tp.getDetail())
                    .fileName(tp.getFileName())
                    .xCoordinate(tp.getXCoordinate())
                    .yCoordinate(tp.getYCoordinate())
                    .zCoordinate(tp.getZCoordinate())
                    .xRotation(tp.getXRotation())
                    .yRotation(tp.getYRotation())
                    .zRotation(tp.getZRotation())
                    .build();

            newDrawing.addPart(part);
        });

        drawingRepository.save(newDrawing);
    }
}