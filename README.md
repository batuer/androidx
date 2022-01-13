# androidx
Flag 操作：
 1.添加 FLAG：mGroupFlags |= FLAG
 2.清除 FLAG：mGroupFlags &= ~FLAG
 3.包含 FLAG：(mGroupFlags & FLAG) != 0 或 (mGroupFlags & FLAG) == FLAG
 4.不包含 FLAG：(mGroupFlags & FLAG) == 0 或 (mGroupFlags & FLAG) != FLAG
