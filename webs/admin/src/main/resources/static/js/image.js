/**
 * 이미지 URL 파싱 및 태그 생성 유틸리티
 */
const ImageUtil = (() => {
  return {
    /**
     * CloudFront 상품 이미지 태그 생성
     */
    getGoodsImageHtml(config = {}) {
      const {
        domain = '',
        goodsId = '',
        imgPath = '',
        seq = '1',
        gb = '',
        width = 70,
        height = 70,
        clazz = '',
        isMain = false
      } = config;

      if (!imgPath) {
        return '<img src="/images/noimage.png" style="width:40px; height:40px;" alt="No Image" />';
      }

      const ext = imgPath.substring(imgPath.lastIndexOf('.'));
      const src = `${domain}/goods/${goodsId}/${goodsId}_${seq}${gb}${ext}?w=${width}&h=${height}&f=webp&q=90`;
      const mainStyle = isMain ? "style='border:1px solid #000'" : '';

      return `<img src="${src}" class="${clazz}" ${mainStyle} onerror="this.src='/images/noimage.png'"/>`;
    }
  };
})();