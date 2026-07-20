export const DEMO_PRODUCTS = [
  {
    id: 1,
    slug: 'air-force-kinetic',
    name: 'Air Force Kinetic',
    brand: 'Nike',
    category: 'lifestyle',
    price: 2890000,
    priceLabel: '2.890.000đ',
    compareAt: '3.290.000đ',
    badge: 'Mới',
    badgeVariant: 'info',
    image:
      'https://images.unsplash.com/photo-1542291026-7eec264c27ff?w=800&q=80&auto=format&fit=crop',
    colors: ['Trắng', 'Đen'],
    sizes: [39, 40, 41, 42, 43],
  },
  {
    id: 2,
    slug: 'ultraboost-city',
    name: 'Ultraboost City',
    brand: 'Adidas',
    category: 'running',
    price: 3590000,
    priceLabel: '3.590.000đ',
    badge: 'Bán chạy',
    badgeVariant: 'commerce',
    image:
      'https://images.unsplash.com/photo-1606107557195-0e29a4b5b4aa?w=800&q=80&auto=format&fit=crop',
    colors: ['Xám', 'Navy'],
    sizes: [40, 41, 42, 43, 44],
  },
  {
    id: 3,
    slug: 'jordan-retro-low',
    name: 'Jordan Retro Low',
    brand: 'Jordan',
    category: 'lifestyle',
    price: 4290000,
    priceLabel: '4.290.000đ',
    image:
      'https://images.unsplash.com/photo-1551107696-a4b0c5a0d9a2?w=800&q=80&auto=format&fit=crop',
    colors: ['Đỏ', 'Đen'],
    sizes: [39, 40, 41, 42],
  },
  {
    id: 4,
    slug: 'new-balance-574',
    name: '574 Core',
    brand: 'New Balance',
    category: 'running',
    price: 2490000,
    priceLabel: '2.490.000đ',
    badge: 'Sale',
    badgeVariant: 'commerce',
    compareAt: '2.990.000đ',
    image:
      'https://images.unsplash.com/photo-1539185441755-769473a23570?w=800&q=80&auto=format&fit=crop',
    colors: ['Xám', 'Xanh'],
    sizes: [38, 39, 40, 41, 42, 43],
  },
  {
    id: 5,
    slug: 'puma-suede-classic',
    name: 'Suede Classic',
    brand: 'Puma',
    category: 'lifestyle',
    price: 1990000,
    priceLabel: '1.990.000đ',
    image:
      'https://images.unsplash.com/photo-1608231387042-66d1773070a5?w=800&q=80&auto=format&fit=crop',
    colors: ['Đen', 'Trắng'],
    sizes: [39, 40, 41, 42],
  },
  {
    id: 6,
    slug: 'converse-chuck-70',
    name: 'Chuck 70 Hi',
    brand: 'Converse',
    category: 'lifestyle',
    price: 1890000,
    priceLabel: '1.890.000đ',
    image:
      'https://images.unsplash.com/photo-1460353581641-37baddab0fa2?w=800&q=80&auto=format&fit=crop',
    colors: ['Trắng', 'Đen', 'Navy'],
    sizes: [37, 38, 39, 40, 41, 42],
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
  return new Intl.NumberFormat('vi-VN', {
    style: 'currency',
    currency: 'VND',
    maximumFractionDigits: 0,
  }).format(amount)
}
