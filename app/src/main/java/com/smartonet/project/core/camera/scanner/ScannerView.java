package com.smartonet.project.core.camera.scanner;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.PointF;
import android.graphics.Rect;
import android.text.TextUtils;
import android.util.AttributeSet;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.BinaryBitmap;
import com.google.zxing.DecodeHintType;
import com.google.zxing.MultiFormatReader;
import com.google.zxing.PlanarYUVLuminanceSource;
import com.google.zxing.Result;
import com.google.zxing.ResultPoint;
import com.google.zxing.common.GlobalHistogramBinarizer;
import com.google.zxing.common.HybridBinarizer;

import java.util.ArrayList;
import java.util.Collections;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;

import cn.bingoogolapple.qrcode.core.BGAQRCodeUtil;
import cn.bingoogolapple.qrcode.core.BarcodeType;
import cn.bingoogolapple.qrcode.core.QRCodeView;
import cn.bingoogolapple.qrcode.core.ScanResult;
import cn.bingoogolapple.qrcode.zxing.QRCodeDecoder;

/**
 * Creat by zhaohan
 * on 2019/4/18
 **/
public class ScannerView extends QRCodeView {
    private MultiFormatReader mMultiFormatReader;
    private Map<DecodeHintType, Object> mHintMap;

    public ScannerView(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, 0);
    }

    public ScannerView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    /**
     * 重写原来的识别，用于提高识别速度，如果可以指定二维码类型，有助于提高识别速度
     */
    @Override
    protected void setupReader() {
        mMultiFormatReader = new MultiFormatReader();
        if (mBarcodeType == BarcodeType.ONE_DIMENSION) {//所有一维条码格式
            Map<DecodeHintType, Object> ONE_DIMENSION_HINT_MAP = new EnumMap<>(DecodeHintType.class);
            List<BarcodeFormat> oneDimenFormatList = new ArrayList<>();
            oneDimenFormatList.add(BarcodeFormat.CODABAR);
            oneDimenFormatList.add(BarcodeFormat.CODE_39);
            oneDimenFormatList.add(BarcodeFormat.CODE_93);
            oneDimenFormatList.add(BarcodeFormat.CODE_128);
            oneDimenFormatList.add(BarcodeFormat.EAN_8);
            oneDimenFormatList.add(BarcodeFormat.EAN_13);
            oneDimenFormatList.add(BarcodeFormat.ITF);
            oneDimenFormatList.add(BarcodeFormat.PDF_417);
            oneDimenFormatList.add(BarcodeFormat.RSS_14);
            oneDimenFormatList.add(BarcodeFormat.RSS_EXPANDED);
            oneDimenFormatList.add(BarcodeFormat.UPC_A);
            oneDimenFormatList.add(BarcodeFormat.UPC_E);
            oneDimenFormatList.add(BarcodeFormat.UPC_EAN_EXTENSION);
            ONE_DIMENSION_HINT_MAP.put(DecodeHintType.POSSIBLE_FORMATS, oneDimenFormatList);
            ONE_DIMENSION_HINT_MAP.put(DecodeHintType.TRY_HARDER, Boolean.TRUE);
            ONE_DIMENSION_HINT_MAP.put(DecodeHintType.CHARACTER_SET, "utf-8");
            mMultiFormatReader.setHints(ONE_DIMENSION_HINT_MAP);
        } else if (mBarcodeType == BarcodeType.TWO_DIMENSION) { //所有二维条码格式
            Map<DecodeHintType, Object> TWO_DIMENSION_HINT_MAP = new EnumMap<>(DecodeHintType.class);
            List<BarcodeFormat> twoDimenFormatList = new ArrayList<>();
            twoDimenFormatList.add(BarcodeFormat.AZTEC);
            twoDimenFormatList.add(BarcodeFormat.DATA_MATRIX);
            twoDimenFormatList.add(BarcodeFormat.MAXICODE);
            twoDimenFormatList.add(BarcodeFormat.QR_CODE);
            TWO_DIMENSION_HINT_MAP.put(DecodeHintType.POSSIBLE_FORMATS, twoDimenFormatList);
            TWO_DIMENSION_HINT_MAP.put(DecodeHintType.TRY_HARDER, Boolean.TRUE);
            TWO_DIMENSION_HINT_MAP.put(DecodeHintType.CHARACTER_SET, "utf-8");
            mMultiFormatReader.setHints(TWO_DIMENSION_HINT_MAP);
        } else if (mBarcodeType == BarcodeType.ONLY_QR_CODE) {//仅 QR_CODE
            Map<DecodeHintType, Object> QR_CODE_HINT_MAP = new EnumMap<>(DecodeHintType.class);
            QR_CODE_HINT_MAP.put(DecodeHintType.POSSIBLE_FORMATS, Collections.singletonList(BarcodeFormat.QR_CODE));
            QR_CODE_HINT_MAP.put(DecodeHintType.TRY_HARDER, Boolean.TRUE);
            QR_CODE_HINT_MAP.put(DecodeHintType.CHARACTER_SET, "utf-8");
            mMultiFormatReader.setHints(QR_CODE_HINT_MAP);
        } else if (mBarcodeType == BarcodeType.ONLY_CODE_128) {//仅 CODE_128
            Map<DecodeHintType, Object> CODE_128_HINT_MAP = new EnumMap<>(DecodeHintType.class);
            CODE_128_HINT_MAP.put(DecodeHintType.POSSIBLE_FORMATS, Collections.singletonList(BarcodeFormat.CODE_128));
            CODE_128_HINT_MAP.put(DecodeHintType.TRY_HARDER, Boolean.TRUE);
            CODE_128_HINT_MAP.put(DecodeHintType.CHARACTER_SET, "utf-8");
            mMultiFormatReader.setHints(CODE_128_HINT_MAP);
        } else if (mBarcodeType == BarcodeType.ONLY_EAN_13) {
            Map<DecodeHintType, Object> EAN_13_HINT_MAP = new EnumMap<>(DecodeHintType.class);
            EAN_13_HINT_MAP.put(DecodeHintType.POSSIBLE_FORMATS, Collections.singletonList(BarcodeFormat.EAN_13));
            EAN_13_HINT_MAP.put(DecodeHintType.TRY_HARDER, Boolean.TRUE);
            EAN_13_HINT_MAP.put(DecodeHintType.CHARACTER_SET, "utf-8");
            mMultiFormatReader.setHints(EAN_13_HINT_MAP);
        } else if (mBarcodeType == BarcodeType.HIGH_FREQUENCY) {
            Map<DecodeHintType, Object> HIGH_FREQUENCY_HINT_MAP = new EnumMap<>(DecodeHintType.class);
            List<BarcodeFormat> highFrequencyFormatList = new ArrayList<>();
            highFrequencyFormatList.add(BarcodeFormat.QR_CODE);
            highFrequencyFormatList.add(BarcodeFormat.UPC_A);
            highFrequencyFormatList.add(BarcodeFormat.EAN_13);
            highFrequencyFormatList.add(BarcodeFormat.CODE_128);

            HIGH_FREQUENCY_HINT_MAP.put(DecodeHintType.POSSIBLE_FORMATS, highFrequencyFormatList);
            HIGH_FREQUENCY_HINT_MAP.put(DecodeHintType.TRY_HARDER, Boolean.TRUE);
            HIGH_FREQUENCY_HINT_MAP.put(DecodeHintType.CHARACTER_SET, "utf-8");
            mMultiFormatReader.setHints(HIGH_FREQUENCY_HINT_MAP);
        } else if (mBarcodeType == BarcodeType.CUSTOM) {
            mMultiFormatReader.setHints(mHintMap);
        } else {
            Map<DecodeHintType, Object> ALL_HINT_MAP = new EnumMap<>(DecodeHintType.class);
            List<BarcodeFormat> allFormatList = new ArrayList<>();
            allFormatList.add(BarcodeFormat.AZTEC);
            allFormatList.add(BarcodeFormat.CODABAR);
            allFormatList.add(BarcodeFormat.CODE_39);
            allFormatList.add(BarcodeFormat.CODE_93);
            allFormatList.add(BarcodeFormat.CODE_128);
            allFormatList.add(BarcodeFormat.DATA_MATRIX);
            allFormatList.add(BarcodeFormat.EAN_8);
            allFormatList.add(BarcodeFormat.EAN_13);
            allFormatList.add(BarcodeFormat.ITF);
            allFormatList.add(BarcodeFormat.MAXICODE);
            allFormatList.add(BarcodeFormat.PDF_417);
            allFormatList.add(BarcodeFormat.QR_CODE);
            allFormatList.add(BarcodeFormat.RSS_14);
            allFormatList.add(BarcodeFormat.RSS_EXPANDED);
            allFormatList.add(BarcodeFormat.UPC_A);
            allFormatList.add(BarcodeFormat.UPC_E);
            allFormatList.add(BarcodeFormat.UPC_EAN_EXTENSION);
            // 可能的编码格式
            ALL_HINT_MAP.put(DecodeHintType.POSSIBLE_FORMATS, allFormatList);
            // 花更多的时间用于寻找图上的编码，优化准确性，但不优化速度
            ALL_HINT_MAP.put(DecodeHintType.TRY_HARDER, Boolean.TRUE);
            // 复杂模式，开启 PURE_BARCODE 模式（带图片 LOGO 的解码方案）
//        ALL_HINT_MAP.put(DecodeHintType.PURE_BARCODE, Boolean.TRUE);
            // 编码字符集
            ALL_HINT_MAP.put(DecodeHintType.CHARACTER_SET, "utf-8");
            mMultiFormatReader.setHints(ALL_HINT_MAP);
        }
    }

    /**
     * 设置识别的格式
     *
     * @param barcodeType 识别的格式
     * @param hintMap     barcodeType 为 BarcodeType.CUSTOM 时，必须指定该值
     */
    public void setType(BarcodeType barcodeType, Map<DecodeHintType, Object> hintMap) {
        mBarcodeType = barcodeType;
        mHintMap = hintMap;

        if (mBarcodeType == BarcodeType.CUSTOM && (mHintMap == null || mHintMap.isEmpty())) {
            throw new RuntimeException("barcodeType 为 BarcodeType.CUSTOM 时 hintMap 不能为空");
        }
        setupReader();
    }

    @Override
    protected ScanResult processBitmapData(Bitmap bitmap) {
        return new ScanResult(QRCodeDecoder.syncDecodeQRCode(bitmap));
    }

    @Override
    protected ScanResult processData(byte[] data, int width, int height, boolean isRetry) {
        Result rawResult = null;
        Rect scanBoxAreaRect = null;

        try {
            PlanarYUVLuminanceSource source;
            scanBoxAreaRect = mScanBoxView.getScanBoxAreaRect(height);
            if (scanBoxAreaRect != null) {
                source = new PlanarYUVLuminanceSource(data, width, height, scanBoxAreaRect.left, scanBoxAreaRect.top, scanBoxAreaRect.width(),
                        scanBoxAreaRect.height(), false);
            } else {
                source = new PlanarYUVLuminanceSource(data, width, height, 0, 0, width, height, false);
            }

            rawResult = mMultiFormatReader.decodeWithState(new BinaryBitmap(new GlobalHistogramBinarizer(source)));
            if (rawResult == null) {
                rawResult = mMultiFormatReader.decodeWithState(new BinaryBitmap(new HybridBinarizer(source)));
                if (rawResult != null) {
                    BGAQRCodeUtil.d("GlobalHistogramBinarizer 没识别到，HybridBinarizer 能识别到");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            mMultiFormatReader.reset();
        }

        if (rawResult == null) {
            return null;
        }

        String result = rawResult.getText();
        if (TextUtils.isEmpty(result)) {
            return null;
        }

        BarcodeFormat barcodeFormat = rawResult.getBarcodeFormat();
        BGAQRCodeUtil.d("格式为：" + barcodeFormat.name());

        // 处理自动缩放和定位点
        boolean isNeedAutoZoom = isNeedAutoZoom(barcodeFormat);
        if (isShowLocationPoint() || isNeedAutoZoom) {
            ResultPoint[] resultPoints = rawResult.getResultPoints();
            final PointF[] pointArr = new PointF[resultPoints.length];
            int pointIndex = 0;
            for (ResultPoint resultPoint : resultPoints) {
                pointArr[pointIndex] = new PointF(resultPoint.getX(), resultPoint.getY());
                pointIndex++;
            }

            if (transformToViewCoordinates(pointArr, scanBoxAreaRect, isNeedAutoZoom, result)) {
                return null;
            }
        }
        return new ScanResult(result);
    }

    private boolean isNeedAutoZoom(BarcodeFormat barcodeFormat) {
        return isAutoZoom() && barcodeFormat == BarcodeFormat.QR_CODE;
    }
}
