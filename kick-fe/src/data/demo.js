import productsJson from './json/products.json'

export const DEMO_BANNERS = [
  {
    id: 1,
    title: 'SUMMER DROP 2026',
    subtitle: 'BỘ SƯU TẬP SNEAKER GIỚI HẠN',
    desc: 'Ưu đãi đến 30% cho các dòng Running & Streetwear mới nhất.',
    image: 'https://images.unsplash.com/photo-1542291026-7eec264c27ff?w=1920&q=85&auto=format&fit=crop',
    ctaText: 'Khám phá ngay',
    ctaLink: '/shop?cat=new',
  },
  {
    id: 2,
    title: 'JORDAN RETRO COLLECTION',
    subtitle: 'BIỂU TƯỢNG BÓNG RỔ BẤT TỬ',
    desc: 'Hàng chính hãng nhập khẩu nguyên hộp, hỗ trợ checkLegit 100%.',
    image: 'https://images.unsplash.com/photo-1551107696-a4b0c5a0d9a2?w=1920&q=85&auto=format&fit=crop',
    ctaText: 'Xem bộ sưu tập',
    ctaLink: '/shop?brand=Jordan',
  },
  {
    id: 3,
    title: 'ULTRABOOST CITY RUN',
    subtitle: 'ÊM ÁI TRÊN TỪNG BƯỚC CHÂN',
    desc: 'Công nghệ đế Boost thế hệ mới tăng cường hiệu suất chạy bộ.',
    image: 'https://images.unsplash.com/photo-1606107557195-0e29a4b5b4aa?w=1920&q=85&auto=format&fit=crop',
    ctaText: 'Mua ngay',
    ctaLink: '/shop?cat=running',
  },
]

export const DEMO_BRANDS = [
  { id: 'nike', name: 'Nike', logo: '⚡' },
  { id: 'adidas', name: 'Adidas', logo: '👟' },
  { id: 'jordan', name: 'Jordan', logo: '🏀' },
  { id: 'new-balance', name: 'New Balance', logo: '🔥' },
  { id: 'puma', name: 'Puma', logo: '🐆' },
  { id: 'converse', name: 'Converse', logo: '⭐' },
  { id: 'vans', name: 'Vans', logo: '🏁' },
  { id: 'asics', name: 'Asics', logo: '🏃' },
]

export const DEMO_BLOGS = [
  {
    id: 1,
    slug: 'top-5-sneaker-streetwear-2026',
    title: 'Top 5 Đôi Sneaker Không Thể Thiếu Trong Tủ Đồ Streetwear 2026',
    category: 'Tư vấn phối đồ',
    date: '24/07/2026',
    author: 'Minh Tuấn - KickVerse Editor',
    image: 'https://images.unsplash.com/photo-1552346154-21d32810aba3?w=800&q=80&auto=format&fit=crop',
    summary: 'Điểm qua 5 phối màu sneaker kinh điển cùng cách mix-match đồ cực ngầu cho các bạn trẻ.',
  },
  {
    id: 2,
    slug: 'huong-dan-ve-sinh-giay-dung-cach',
    title: 'Hướng Dẫn Vệ Sinh Giày Sneaker Đúng Cách Tại Nhà Tránh Ố Vàng',
    category: 'Mẹo bảo quản',
    date: '20/07/2026',
    author: 'Hoàng Nam',
    image: 'https://images.unsplash.com/photo-1595950653106-6c9ebd614d3a?w=800&q=80&auto=format&fit=crop',
    summary: 'Bí quyết bảo quản và giặt giày da/da lộn không lo bong tróc đế hay phai màu.',
  },
  {
    id: 3,
    slug: 'xu-huong-chieu-sau-technology-boost',
    title: 'Công Nghệ Đệm Giày Đột Phá Giúp Bạn Chạy Bộ Không Mệt Mỏi',
    category: 'Tin tức',
    date: '15/07/2026',
    author: 'KickVerse Team',
    image: 'https://images.unsplash.com/photo-1460353581641-37baddab0fa2?w=800&q=80&auto=format&fit=crop',
    summary: 'Khám phá các cấu trúc đến Eva, Nitrogen-injected foam mới nhất của các đại gia thể thao.',
  },
]

export const DEMO_PRODUCTS = productsJson

export const DEMO_REVIEWS = [
  {
    id: 101,
    user: 'Tuấn Anh',
    avatar: 'https://i.pravatar.cc/100?img=33',
    rating: 5,
    date: '18/07/2026',
    variant: 'Size 41 - Màu Trắng',
    content: 'Đôi này đi êm cực kỳ, form chuẩn đúng như mô tả. Đóng gói 2 hộp cẩn thận, đính kèm phiếu bảo hành chính hãng.',
    photos: [
      'https://images.unsplash.com/photo-1542291026-7eec264c27ff?w=400&q=80&auto=format&fit=crop',
    ],
  },
  {
    id: 102,
    user: 'Thảo My',
    avatar: 'https://i.pravatar.cc/100?img=47',
    rating: 5,
    date: '12/07/2026',
    variant: 'Size 39 - Màu Đen',
    content: 'Giao hàng siêu nhanh trong 24h ở Hà Nội. Giày chuẩn auth check legit thoải mái.',
    photos: [],
  },
  {
    id: 103,
    user: 'Đức Huy',
    avatar: 'https://i.pravatar.cc/100?img=12',
    rating: 4,
    date: '05/07/2026',
    variant: 'Size 42 - Màu Xám',
    content: 'Đế đi rất nảy, hơi chật ở phần mũi nên ai chân bè khuyên nhích 0.5 size nha.',
    photos: [],
  },
]

export const DEMO_VOUCHERS = [
  {
    code: 'KICK100',
    title: 'Giảm 100.000đ',
    desc: 'Cho đơn hàng từ 1.500.000đ',
    discount: 100000,
    minSpend: 1500000,
    expiry: '31/08/2026',
  },
  {
    code: 'FREESHIP2026',
    title: 'Miễn phí vận chuyển',
    desc: 'Giảm tối đa 40k phí giao hàng',
    discount: 40000,
    minSpend: 500000,
    expiry: '15/09/2026',
  },
  {
    code: 'KICKVIP20',
    title: 'Giảm 20% tối đa 300k',
    desc: 'Ưu đãi cho khách hàng VIP KickVerse',
    discountPercent: 20,
    maxDiscount: 300000,
    minSpend: 2000000,
    expiry: '31/12/2026',
  },
]

export const DEMO_NOTIFICATIONS = [
  {
    id: 1,
    type: 'ORDER_STATUS',
    title: 'Đơn #KV-1042 đang giao',
    body: 'Đơn hàng của bạn đã rời kho và đang trên đường giao.',
    read: false,
    createdAt: '5 phút trước',
  },
  {
    id: 2,
    type: 'PROMOTION',
    title: 'Flash sale cuối tuần',
    body: 'Giảm đến 30% cho dòng running — chỉ đến Chủ nhật.',
    read: false,
    createdAt: '1 giờ trước',
  },
  {
    id: 3,
    type: 'SYSTEM',
    title: 'Cập nhật chính sách đổi trả',
    body: 'Thời gian đổi size được mở rộng lên 14 ngày.',
    read: true,
    createdAt: 'Hôm qua',
  },
]

export function formatVnd(amount) {
  if (!amount && amount !== 0) return '0đ'
  return new Intl.NumberFormat('vi-VN', {
    style: 'currency',
    currency: 'VND',
    maximumFractionDigits: 0,
  }).format(amount)
}
